// import axios from 'axios'
import request from '@/utils/request'

// export function treelist() {
//   return axios.get('/metadata/getQueryOrScheduleZtreeNode.do?needTable=0')
// }

export function treelist(query) {
  return request({
    url: '/Tree/list',
    method: 'get',
    params: query
  })
}
