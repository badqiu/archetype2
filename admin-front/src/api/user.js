import request from '@/utils/request'

// export function logout(query) {
//   return request({
//     url: '/user/logout',
//     method: 'get',
//     params: query
//   })
// }

export function getPage(query) {
  return request({
    url: '/admin/user/getPage',
    method: 'get',
    params: query
  })
}

export function getList(query) {
  return request({
    url: '/admin/user/getList',
    method: 'get',
    params: query
  })
}

export function getById(query) {
  return request({
    url: '/admin/user/getById',
    method: 'get',
    params: query
  })
}

export function getOne(query) {
  return request({
    url: '/admin/user/getOne',
    method: 'get',
    params: query
  })
}

export function saveOrUpdate(query) {
  return request({
    url: '/admin/user/saveOrUpdate',
    method: 'post',
    data: query
  })
}

export function removeById(query) {
  return request({
    url: '/admin/user/removeById',
    method: 'post',
    params: query
  })
}

export function remove(query) {
  return request({
    url: '/admin/user/remove',
    method: 'post',
    params: query
  })
}

export function removeByMap(query) {
  return request({
    url: '/admin/user/removeByMap',
    method: 'post',
    params: query
  })
}
