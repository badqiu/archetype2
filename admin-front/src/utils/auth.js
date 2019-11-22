import Cookies from 'js-cookie'

const TokenKey = 'Admin-Token'
const Passport = 'passport'
const SsoValidTime = 'ssoValidTime'
const SsoValidKey = 'ssoValidKey'

export function getToken() {
  return Cookies.get(Passport)
}

export function setToken(token) {
  return Cookies.set(TokenKey, token)
}

export function removeToken() {
  return Cookies.remove(TokenKey)
}

export function getPassport() {
  return Cookies.get(Passport)
}

export function setPassport(passport) {
  return Cookies.set(Passport, passport)
}

export function removePassport() {
  return Cookies.remove(Passport)
}

export function getSsoValidTime() {
  return Cookies.get(SsoValidTime)
}

export function setSsoValidTime(ssoValidTime) {
  return Cookies.set(SsoValidTime, ssoValidTime)
}

export function removeSsoValidTime() {
  return Cookies.remove(SsoValidTime)
}

export function getSsoValidKey() {
  return Cookies.get(SsoValidKey)
}

export function setSsoValidKey(ssoValidKey) {
  return Cookies.set(SsoValidKey, ssoValidKey)
}

export function removeSsoValidKey() {
  Cookies.remove(SsoValidKey)
}
