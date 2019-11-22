import router from './router'
import NProgress from 'nprogress'
import 'nprogress/nprogress.css'
import {
  getPassport,
  setPassport,
  setSsoValidTime,
  setSsoValidKey,
  getSsoValidKey,
  getSsoValidTime
} from '@/utils/auth'

NProgress.configure({ showSpinner: false })

function toLoginPage(fromUrl) {
  location.href = 'https://dcauth.xoyo.com/sso.do?redirectURL=http://localhost:9528' + fromUrl
}

const whiteList = ['/404', '/401']

router.beforeEach((to, from, next) => {
  NProgress.start()
  if (to.query.passport && to.query.ssoValidTime && to.query.ssoValidKey) {
    setPassport(to.query.passport)
    setSsoValidTime(to.query.ssoValidTime)
    setSsoValidKey(to.query.ssoValidKey)
  }
  if (whiteList.indexOf(to.path) !== -1) {
    next()
  } else {
    if (getPassport() && getSsoValidTime() && getSsoValidKey()) {
      next()
    } else {
      NProgress.done()
      toLoginPage(to.fullPath)
    }
  }
})

router.afterEach(() => {
  NProgress.done() // 结束Progress
})
