webpackJsonp([26],{kQrA:function(e,a,n){"use strict";Object.defineProperty(a,"__esModule",{value:!0});var i=n("fA45"),s=n("+Xkk"),t=n("CqtB"),r={name:"AdminUsers",components:{AdminSidebar:s.a,AdminSearch:i.a,FriendsBlock:t.a},data:function(){return{filter:"all",search:""}},methods:{onChangeFilter:function(e){this.filter=e},onChangeSearch:function(e){this.search=e}}},l={render:function(){var e=this,a=e.$createElement,n=e._self._c||a;return n("div",{staticClass:"admin-users inner-page admin"},[n("h2",{staticClass:"admin__title"},[e._v("Люди")]),n("div",{staticClass:"admin__wrap"},[n("div",{staticClass:"inner-page__main"},[n("div",{staticClass:"admin__search"},[n("admin-search",{on:{"change-value":e.onChangeSearch},model:{value:e.search,callback:function(a){e.search=a},expression:"search"}})],1),n("div",{staticClass:"friends__list"},[n("friends-block",{attrs:{admin:"admin"}}),n("friends-block",{attrs:{admin:"admin",blocked:"blocked"}})],1)]),n("div",{staticClass:"inner-page__aside"},[n("admin-sidebar",{on:{"change-filter":e.onChangeFilter},model:{value:e.filter,callback:function(a){e.filter=a},expression:"filter"}})],1)])])},staticRenderFns:[]},c=n("VU/8")(r,l,!1,null,null,null);a.default=c.exports}});
//# sourceMappingURL=26.d8f37e029c992d16d151.js.map