webpackJsonp([9],{Eu1L:function(t,e,i){"use strict";Object.defineProperty(e,"__esModule",{value:!0});var n=i("Xxa5"),o=i.n(n),a=i("exGp"),s=i.n(a),l=i("Dd8w"),r=i.n(l),c=i("UBpT"),f=i("tJ7h"),d=i("7PTA"),_=i("0Hd5"),p=i("NYxO"),u={name:"Profile",components:{FriendsPossible:c.a,ProfileInfo:f.a,NewsAdd:d.a,NewsBlock:_.a},data:function(){return{activeTab:"POSTED"}},computed:r()({},Object(p.c)("profile/info",["getInfo"]),Object(p.c)("users/info",["getWall","getWallPostedLength","getWallQueuedLength"]),{activeWall:function(){var t=this;return this.getWall.filter(function(e){return e.type===t.activeTab})}}),methods:r()({},Object(p.b)("profile/info",["apiInfo"]),Object(p.b)("users/info",["apiWall"]),{changeTab:function(t){this.activeTab=t}}),created:function(){var t=this;return s()(o.a.mark(function e(){return o.a.wrap(function(e){for(;;)switch(e.prev=e.next){case 0:return e.next=2,t.apiInfo();case 2:t.getInfo&&t.apiWall({id:t.getInfo.id});case 3:case"end":return e.stop()}},e,t)}))()}},v={render:function(){var t=this,e=t.$createElement,i=t._self._c||e;return i("div",{staticClass:"profile inner-page"},[i("div",{staticClass:"inner-page__main"},[i("div",{staticClass:"profile__info"},[i("profile-info",{attrs:{me:"me",online:"online",info:t.getInfo}})],1),i("div",{staticClass:"profile__news"},[i("div",{staticClass:"profile__tabs"},[i("span",{staticClass:"profile__tab",class:{active:"published"===t.activeTab},on:{click:function(e){return t.changeTab("POSTED")}}},[t._v("Мои публикации ("+t._s(t.getWallPostedLength)+")")]),t.getWallQueuedLength>0?i("span",{staticClass:"profile__tab",class:{active:"queue"===t.activeTab},on:{click:function(e){return t.changeTab("QUEUED")}}},[t._v("Отложенные публикации ("+t._s(t.getWallQueuedLength)+")")]):t._e()]),i("div",{staticClass:"profile__add"},[i("news-add")],1),t.getWall.length>0?i("div",{staticClass:"profile__news-list"},t._l(t.activeWall,function(e){return i("news-block",{key:e.id,attrs:{edit:"edit",deleted:"deleted",deffered:"queue"===t.activeTab,info:e}})}),1):t._e()])]),i("div",{staticClass:"inner-page__aside"},[i("friends-possible")],1)])},staticRenderFns:[]},h=i("VU/8")(u,v,!1,null,null,null);e.default=h.exports},VM8i:function(t,e){},tJ7h:function(t,e,i){"use strict";var n=i("Dd8w"),o=i.n(n),a=i("NYxO"),s={name:"ProfileInfo",components:{Modal:i("/o5o").a},props:{me:Boolean,online:Boolean,blocked:Boolean,friend:Boolean,info:Object,youBlocked:Boolean},data:function(){return{modalShow:!1,modalText:"",modalType:"deleteFriend"}},computed:o()({},Object(a.c)("profile/dialogs",["dialogs"]),{statusText:function(){return this.online?"онлайн":"не в сети"},blockedText:function(){return this.blocked?"Пользователь заблокирован":"Заблокировать"},btnVariantInfo:function(){return this.blocked?{variant:"red",text:"Разблокировать"}:this.friend?{variant:"red",text:"Удалить из друзей",disableButton:!0}:{variant:"white",text:"Добавить в друзья",disableButton:!0}},getAge:function(){return t=this.info.ages,e=["год","года","лет"],i=(t=Math.abs(t)%100)%10,t>10&&t<20?e[2]:i>1&&i<5?e[1]:1===i?e[0]:e[2];var t,e,i}}),methods:o()({},Object(a.b)("users/actions",["apiBlockUser","apiUnblockUser"]),Object(a.b)("profile/friends",["apiAddFriends","apiDeleteFriends"]),Object(a.b)("profile/dialogs",["createDialogWithUser","apiLoadAllDialogs"]),Object(a.b)("users/info",["apiInfo"]),{blockedUser:function(){this.blocked||(this.modalText="Вы уверены, что хотите заблокировать пользователя "+this.info.fullName,this.modalShow=!0,this.modalType="block")},profileAction:function(){var t=this;if(!this.blocked)return this.friend?(this.modalText="Вы уверены, что хотите удалить пользователя "+this.info.fullName+" из друзей?",this.modalShow=!0,void(this.modalType="deleteFriend")):void this.apiAddFriends(this.info.id).then(function(){t.apiInfo(t.$route.params.id)});this.apiUnblockUser(this.$route.params.id).then(function(){t.apiInfo(t.$route.params.id)})},closeModal:function(){this.modalShow=!1},onConfirm:function(){var t=this;"block"!==this.modalType?this.apiDeleteFriends(this.$route.params.id).then(function(){t.apiInfo(t.$route.params.id),t.closeModal()}):this.apiBlockUser(this.$route.params.id).then(function(){t.apiInfo(t.$route.params.id),t.closeModal()})},onSentMessage:function(){if(this.blocked)return!1;this.$router.push({name:"Im",query:{userId:this.info.id}})}})},l={render:function(){var t=this,e=t.$createElement,i=t._self._c||e;return t.info?i("div",{staticClass:"profile-info"},[i("div",{staticClass:"profile-info__pic"},[i("div",{staticClass:"profile-info__img",class:{offline:!t.online&&!t.me}},[i("img",{attrs:{src:t.info.photo,alt:t.info.fullName}})]),t.me?t._e():i("div",{staticClass:"profile-info__actions"},[t.youBlocked?i("div",{staticClass:"profile-info__actions__you-blocked"},[i("span",{staticClass:"profile-info__you-blocked"},[t._v("Пользователь вас заблокировал")])]):i("div",{staticClass:"profile-info__actions__you-not-blocked"},[i("button-hover",{attrs:{disable:t.blocked},nativeOn:{click:function(e){return t.onSentMessage(e)}}},[t._v("Написать сообщение")]),i("button-hover",{staticClass:"profile-info__add",attrs:{variant:t.btnVariantInfo.variant,bordered:"bordered"},nativeOn:{click:function(e){return t.profileAction(e)}}},[t._v(t._s(t.btnVariantInfo.text))])],1)])]),i("div",{staticClass:"profile-info__main"},[t.me?i("router-link",{staticClass:"edit",attrs:{to:{name:"Settings"}}},[i("simple-svg",{attrs:{filepath:"/static/img/edit.svg"}})],1):i("span",{staticClass:"profile-info__blocked",class:{blocked:t.blocked},on:{click:t.blockedUser}},[t._v(t._s(t.blockedText))]),i("div",{staticClass:"profile-info__header"},[i("h1",{staticClass:"profile-info__name"},[t._v(t._s(t.info.fullName))]),i("span",{staticClass:"user-status",class:{online:t.online,offline:!t.online}},[t._v(t._s(t.statusText))])]),i("div",{staticClass:"profile-info__block"},[i("span",{staticClass:"profile-info__title"},[t._v("Дата рождения:")]),t.info.birth_date?i("span",{staticClass:"profile-info__val"},[t._v(t._s(t._f("moment")(t.info.birth_date,"D MMMM YYYY"))+" ("+t._s(t.info.ages)+" "+t._s(t.getAge)+")")]):i("span",{staticClass:"profile-info__val"},[t._v("не заполнено")])]),i("div",{staticClass:"profile-info__block"},[i("span",{staticClass:"profile-info__title"},[t._v("Телефон:")]),t.info.phone?i("a",{staticClass:"profile-info__val",attrs:{href:"tel:"+t.info.phone}},[t._v(t._s(t._f("phone")(t.info.phone)))]):i("a",{staticClass:"profile-info__val"},[t._v("не заполнено")])]),i("div",{staticClass:"profile-info__block"},[i("span",{staticClass:"profile-info__title"},[t._v("Страна, город:")]),t.info.country?i("span",{staticClass:"profile-info__val"},[t._v(t._s(t.info.country.title)+", "+t._s(t.info.city.title))]):i("span",{staticClass:"profile-info__val"},[t._v("не заполнено")])]),i("div",{staticClass:"profile-info__block"},[i("span",{staticClass:"profile-info__title"},[t._v("О себе:")]),t.info.about?i("span",{staticClass:"profile-info__val"},[t._v(t._s(t.info.about))]):i("span",{staticClass:"profile-info__val"},[t._v("не заполнено")])])],1),i("modal",{model:{value:t.modalShow,callback:function(e){t.modalShow=e},expression:"modalShow"}},[t.modalText?i("p",[t._v(t._s(t.modalText))]):t._e(),i("template",{slot:"actions"},[i("button-hover",{nativeOn:{click:function(e){return e.preventDefault(),t.onConfirm(e)}}},[t._v("Да")]),i("button-hover",{attrs:{variant:"red",bordered:"bordered"},nativeOn:{click:function(e){return t.closeModal(e)}}},[t._v("Отмена")])],1)],2)],1):t._e()},staticRenderFns:[]};var r=i("VU/8")(s,l,!1,function(t){i("VM8i")},null,null);e.a=r.exports}});
//# sourceMappingURL=9.cb20ce605b0eac5309c8.js.map