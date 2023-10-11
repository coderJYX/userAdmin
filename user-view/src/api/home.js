import Request from "@/utils/request";

export function getUnitList() {
    return Request({
        url: '/user/unitList',
        method: "get"
    })
}
export function getList(data) {
    return Request({
        url: '/user/list',
        method: "post",
        data
    })
}
export function loginOut(token) {
    return Request({
        url: '/user/loginOut?token=' + token,
        method: "get",
    })
}
export function deleteById(id) {
    return Request({
        url: '/user/deleteById?id=' + id,
        method: "get",
    })
}
export function add(data) {
    return Request({
        url: '/user/add',
        method: "post",
        data
    })
}
export function update(data) {
    return Request({
        url: '/user/update',
        method: "post",
        data
    })
}
export function detail(id) {
    return Request({
        url: '/user/detail?id=' + id,
        method: "get",
    })
}