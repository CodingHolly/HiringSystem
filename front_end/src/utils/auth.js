import Cookies from 'js-cookie';

const TokenKey = 'vue_admin_template_token';

// 获取Cookies
export function getToken() {
    return Cookies.get(TokenKey);
}
// 设置Cookies
export function setToken(token) {
    return Cookies.set(TokenKey, token);
}
// 移除Cookies
export function removeToken() {
    return Cookies.remove(TokenKey);
}
