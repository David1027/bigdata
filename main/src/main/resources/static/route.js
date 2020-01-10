// // 全局路由各种钩子
// const { ar_main } = require('../static/bigdata/buriedpoint.js')
// export default ({
//   app, store
// }) => {
//   app.router.afterEach((to, from) => {
//     window.scrollTo(0, 0)
//     console.log('to-------', to.fullPath)
//     const email = store.state.member.user.email
//     const mobile = store.state.member.user.mobile
//     setTimeout(() => {
//       let url
//       if (!sessionStorage.getItem('ref')) {
//         url = '/'
//       } else {
//         url = sessionStorage.getItem('ref') ? from.fullPath : document.referrer
//       }
//       sessionStorage.setItem('ref', url)
//       console.log('url', url)
//       ar_main(url, email, mobile)
//     }, 500)
//   })
// }
