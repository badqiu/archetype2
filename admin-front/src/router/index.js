import Vue from 'vue'
import Router from 'vue-router'

// in development-env not use lazy-loading, because lazy-loading too many pages will cause webpack hot update too slow. so only in production use lazy-loading;
// detail: https://panjiachen.github.io/vue-element-admin-site/#/lazy-loading

Vue.use(Router)

/* Layout */
import LayoutIndex from '../views/layout/LayoutIndex' // 首页导航
import LayoutFirst from '../views/layout/LayoutFirst' // 一级导航
import LayoutSecond from '../views/layout/LayoutSecond' // 二级导航
import LayoutZtree from '../views/layout/LayoutZtree' // ztree树导航

// import defaultRouter from './defaultRouter' // 默认导航

/**
* hidden: true                   if `hidden:true` will not show in the sidebar(default is false)
* alwaysShow: true               if set true, will always show the root menu, whatever its child routes length
*                                if not set alwaysShow, only more than one route under the children
*                                it will becomes nested mode, otherwise not show the root menu
* redirect: noredirect           if `redirect:noredirect` will no redirect in the breadcrumb
* name:'router-name'             the name is used by <keep-alive> (must set!!!)
* meta : {
    title: 'title'               the name show in subMenu and breadcrumb (recommend set)
    icon: 'svg-name'             the icon show in the sidebar
    breadcrumb: false            if false, the item will hidden in breadcrumb(default is true)
  }
**/

export const constantRouterMap = [
  { path: '/404', component: () => import('@/views/404'), hidden: true },

  {
    path: '/',
    redirect: '/index'
  },

  {
    path: '/index',
    component: LayoutIndex,
    redirect: '/index/user',
    name: 'index',
    children: [
      {
        path: '/index/user',
        name: 'user',
        component: () => import('@/views/index/user'),
        meta: { title: '用户管理', icon: 'table' }
      }
    ]
  },

  {
    path: '/first',
    component: LayoutFirst,
    redirect: '/first/all',
    name: 'first',
    children: [
      {
        path: '/first/:projectId',
        name: 'tableDataList',
        component: () => import('@/views/tableData/index'),
        meta: { title: '表格数据', icon: 'table' }
      }
    ]
  },

  {
    path: '/second',
    component: LayoutSecond,
    redirect: '/second/all',
    name: 'second',
    hidden: true, // 不显示
    children: [
      {
        path: '/second/:projectId',
        name: 'second',
        component: () => import('@/views/tableData/index'),
        meta: { title: '表格数据', icon: 'table' }
      }
    ]
  },

  {
    path: '/ztree',
    redirect: '/ztree',
    component: LayoutZtree,
    hidden: true, // 不显示
    children: [
      {
        path: '/ztree',
        name: 'ztree',
        component: () => import('@/views/tableData/index'),
        meta: { title: 'ztree数导航', icon: 'table' }
      }
    ]
  },

  { path: '*', redirect: '/404', hidden: true }
]

export default new Router({
  mode: 'history',
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRouterMap
})
