import request from '@/utils/request'

export function selectPage(query) {
  return request({
    url: '/admin/crawler/selectPage',
    method: 'get',
    params: query
  })
}

export function insertOrUpdate(query) {
  return request({
    url: '/admin/crawler/insertOrUpdate',
    method: 'post',
    data: query
  })
}

export function deleteById(query) {
  return request({
    url: '/admin/crawler/deleteById',
    method: 'post',
    params: query
  })
}

export function isCrawlerNameExist(query) {
  return request({
    url: '/admin/crawler/isCrawlerNameExist',
    method: 'get',
    params: query
  })
}

export function isCrawlerCodeExist(query) {
  return request({
    url: '/admin/crawler/isCrawlerCodeExist',
    method: 'get',
    params: query
  })
}
