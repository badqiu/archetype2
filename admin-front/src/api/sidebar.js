import request from '@/utils/request'

export function SidebarItemList(query) {
  return request({
    url: '/SidebarItem/list',
    method: 'get',
    params: query
  })
}
