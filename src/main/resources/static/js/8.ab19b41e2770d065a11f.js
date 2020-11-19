webpackJsonp([8],{VM8i:function(e,i){},tJ7h:function(e,i,t){"use strict";var o=t("Dd8w"),s=t.n(o),n=t("NYxO"),a={name:"ProfileInfo",components:{Modal:t("/o5o").a},props:{me:Boolean,online:Boolean,blocked:Boolean,friend:Boolean,info:Object,youBlocked:Boolean},data:function(){return{modalShow:!1,modalText:"",modalType:"deleteFriend"}},computed:s()({},Object(n.c)("profile/dialogs",["dialogs"]),{statusText:function(){return this.online?"онлайн":"не в сети"},blockedText:function(){return this.blocked?"Пользователь заблокирован":"Заблокировать"},btnVariantInfo:function(){return this.blocked?{variant:"red",text:"Разблокировать"}:this.friend?{variant:"red",text:"Удалить из друзей",disableButton:!0}:{variant:"white",text:"Добавить в друзья",disableButton:!0}},getAge:function(){return e=this.info.ages,i=["год","года","лет"],t=(e=Math.abs(e)%100)%10,e>10&&e<20?i[2]:t>1&&t<5?i[1]:1===t?i[0]:i[2];var e,i,t}}),methods:s()({},Object(n.b)("users/actions",["apiBlockUser","apiUnblockUser"]),Object(n.b)("profile/friends",["apiAddFriends","apiDeleteFriends"]),Object(n.b)("profile/dialogs",["createDialogWithUser","apiLoadAllDialogs"]),Object(n.b)("users/info",["apiInfo"]),{blockedUser:function(){this.blocked||(this.modalText="Вы уверены, что хотите заблокировать пользователя "+this.info.fullName,this.modalShow=!0,this.modalType="block")},profileAction:function(){var e=this;if(!this.blocked)return this.friend?(this.modalText="Вы уверены, что хотите удалить пользователя "+this.info.fullName+" из друзей?",this.modalShow=!0,void(this.modalType="deleteFriend")):void this.apiAddFriends(this.info.id).then(function(){e.apiInfo(e.$route.params.id)});this.apiUnblockUser(this.$route.params.id).then(function(){e.apiInfo(e.$route.params.id)})},closeModal:function(){this.modalShow=!1},onConfirm:function(){var e=this;"block"!==this.modalType?this.apiDeleteFriends(this.$route.params.id).then(function(){e.apiInfo(e.$route.params.id),e.closeModal()}):this.apiBlockUser(this.$route.params.id).then(function(){e.apiInfo(e.$route.params.id),e.closeModal()})},onSentMessage:function(){if(this.blocked)return!1;this.$router.push({name:"Im",query:{userId:this.info.id}})}})},l={render:function(){var e=this,i=e.$createElement,t=e._self._c||i;return e.info?t("div",{staticClass:"profile-info"},[t("div",{staticClass:"profile-info__pic"},[t("div",{staticClass:"profile-info__img",class:{offline:!e.online&&!e.me}},[t("img",{attrs:{src:e.info.photo,alt:e.info.fullName}})]),e.me?e._e():t("div",{staticClass:"profile-info__actions"},[e.youBlocked?t("div",{staticClass:"profile-info__actions__you-blocked"},[t("span",{staticClass:"profile-info__you-blocked"},[e._v("Пользователь вас заблокировал")])]):t("div",{staticClass:"profile-info__actions__you-not-blocked"},[t("button-hover",{attrs:{disable:e.blocked},nativeOn:{click:function(i){return e.onSentMessage(i)}}},[e._v("Написать сообщение")]),t("button-hover",{staticClass:"profile-info__add",attrs:{variant:e.btnVariantInfo.variant,bordered:"bordered"},nativeOn:{click:function(i){return e.profileAction(i)}}},[e._v(e._s(e.btnVariantInfo.text))])],1)])]),t("div",{staticClass:"profile-info__main"},[e.me?t("router-link",{staticClass:"edit",attrs:{to:{name:"Settings"}}},[t("simple-svg",{attrs:{filepath:"/static/img/edit.svg"}})],1):t("span",{staticClass:"profile-info__blocked",class:{blocked:e.blocked},on:{click:e.blockedUser}},[e._v(e._s(e.blockedText))]),t("div",{staticClass:"profile-info__header"},[t("h1",{staticClass:"profile-info__name"},[e._v(e._s(e.info.fullName))]),t("span",{staticClass:"user-status",class:{online:e.online,offline:!e.online}},[e._v(e._s(e.statusText))])]),t("div",{staticClass:"profile-info__block"},[t("span",{staticClass:"profile-info__title"},[e._v("Дата рождения:")]),e.info.birth_date?t("span",{staticClass:"profile-info__val"},[e._v(e._s(e._f("moment")(e.info.birth_date,"D MMMM YYYY"))+" ("+e._s(e.info.ages)+" "+e._s(e.getAge)+")")]):t("span",{staticClass:"profile-info__val"},[e._v("не заполнено")])]),t("div",{staticClass:"profile-info__block"},[t("span",{staticClass:"profile-info__title"},[e._v("Телефон:")]),e.info.phone?t("a",{staticClass:"profile-info__val",attrs:{href:"tel:"+e.info.phone}},[e._v(e._s(e._f("phone")(e.info.phone)))]):t("a",{staticClass:"profile-info__val"},[e._v("не заполнено")])]),t("div",{staticClass:"profile-info__block"},[t("span",{staticClass:"profile-info__title"},[e._v("Страна, город:")]),e.info.country?t("span",{staticClass:"profile-info__val"},[e._v(e._s(e.info.country.title)+", "+e._s(e.info.city.title))]):t("span",{staticClass:"profile-info__val"},[e._v("не заполнено")])]),t("div",{staticClass:"profile-info__block"},[t("span",{staticClass:"profile-info__title"},[e._v("О себе:")]),e.info.about?t("span",{staticClass:"profile-info__val"},[e._v(e._s(e.info.about))]):t("span",{staticClass:"profile-info__val"},[e._v("не заполнено")])])],1),t("modal",{model:{value:e.modalShow,callback:function(i){e.modalShow=i},expression:"modalShow"}},[e.modalText?t("p",[e._v(e._s(e.modalText))]):e._e(),t("template",{slot:"actions"},[t("button-hover",{nativeOn:{click:function(i){return i.preventDefault(),e.onConfirm(i)}}},[e._v("Да")]),t("button-hover",{attrs:{variant:"red",bordered:"bordered"},nativeOn:{click:function(i){return e.closeModal(i)}}},[e._v("Отмена")])],1)],2)],1):e._e()},staticRenderFns:[]};var r=t("VU/8")(a,l,!1,function(e){t("VM8i")},null,null);i.a=r.exports},xX9V:function(e,i,t){"use strict";Object.defineProperty(i,"__esModule",{value:!0});var o=t("Dd8w"),s=t.n(o),n=t("UBpT"),a=t("tJ7h"),l=t("0Hd5"),r=t("NYxO"),f={name:"ProfileId",components:{FriendsPossible:n.a,ProfileInfo:a.a,NewsBlock:l.a},data:function(){return{loading:!1}},computed:s()({},Object(r.c)("users/info",["getUsersInfo","getWallPosted"])),methods:s()({},Object(r.b)("users/info",["userInfoId"])),created:function(){this.userInfoId(this.$route.params.id)}},c={render:function(){var e=this,i=e.$createElement,t=e._self._c||i;return e.getUsersInfo?t("div",{staticClass:"profile inner-page"},[t("div",{staticClass:"inner-page__main"},[t("div",{staticClass:"profile__info"},[t("profile-info",{attrs:{info:e.getUsersInfo,blocked:e.getUsersInfo.is_blocked,friend:e.getUsersInfo.is_friend,online:e.getUsersInfo.is_onlined,youBlocked:e.getUsersInfo.are_you_blocked,me:e.getUsersInfo.is_me}})],1),e.getUsersInfo.are_you_blocked?e._e():t("div",{staticClass:"profile__news"},[t("div",{staticClass:"profile__tabs"},[t("span",{staticClass:"profile__tab active"},[e._v("Публикации "+e._s(e.getUsersInfo.first_name)+" ("+e._s(e.getWallPosted.length)+")")])]),t("div",{staticClass:"profile__news-list"},e._l(e.getWallPosted,function(e){return t("news-block",{key:e.id,attrs:{info:e}})}),1)])]),t("div",{staticClass:"inner-page__aside"},[t("friends-possible")],1)]):e._e()},staticRenderFns:[]},d=t("VU/8")(f,c,!1,null,null,null);i.default=d.exports}});
//# sourceMappingURL=8.ab19b41e2770d065a11f.js.map