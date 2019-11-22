import axios from 'axios'

export function getMyMenus() {
  return axios.get('/getMyMenus.do')
}

