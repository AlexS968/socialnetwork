webpackJsonp([4],{H6Pi:function(e,t){},LcYm:function(e,t,n){"use strict";Object.defineProperty(t,"__esModule",{value:!0});var r=n("tma6");t.load=r.load,t.getInstance=r.getInstance;var a=n("LztK");t.ReCaptchaInstance=a.ReCaptchaInstance},LztK:function(e,t,n){"use strict";var r=this&&this.__awaiter||function(e,t,n,r){return new(n||(n=Promise))(function(a,i){function o(e){try{c(r.next(e))}catch(e){i(e)}}function s(e){try{c(r.throw(e))}catch(e){i(e)}}function c(e){var t;e.done?a(e.value):(t=e.value,t instanceof n?t:new n(function(e){e(t)})).then(o,s)}c((r=r.apply(e,t||[])).next())})},a=this&&this.__generator||function(e,t){var n,r,a,i,o={label:0,sent:function(){if(1&a[0])throw a[1];return a[1]},trys:[],ops:[]};return i={next:s(0),throw:s(1),return:s(2)},"function"==typeof Symbol&&(i[Symbol.iterator]=function(){return this}),i;function s(i){return function(s){return function(i){if(n)throw new TypeError("Generator is already executing.");for(;o;)try{if(n=1,r&&(a=2&i[0]?r.return:i[0]?r.throw||((a=r.return)&&a.call(r),0):r.next)&&!(a=a.call(r,i[1])).done)return a;switch(r=0,a&&(i=[2&i[0],a.value]),i[0]){case 0:case 1:a=i;break;case 4:return o.label++,{value:i[1],done:!1};case 5:o.label++,r=i[1],i=[0];continue;case 7:i=o.ops.pop(),o.trys.pop();continue;default:if(!(a=(a=o.trys).length>0&&a[a.length-1])&&(6===i[0]||2===i[0])){o=0;continue}if(3===i[0]&&(!a||i[1]>a[0]&&i[1]<a[3])){o.label=i[1];break}if(6===i[0]&&o.label<a[1]){o.label=a[1],a=i;break}if(a&&o.label<a[2]){o.label=a[2],o.ops.push(i);break}a[2]&&o.ops.pop(),o.trys.pop();continue}i=t.call(e,o)}catch(e){i=[6,e],r=0}finally{n=a=0}if(5&i[0])throw i[1];return{value:i[0]?i[1]:void 0,done:!0}}([i,s])}}};Object.defineProperty(t,"__esModule",{value:!0});var i=function(){function e(e,t,n){this.siteKey=e,this.recaptchaID=t,this.recaptcha=n,this.styleContainer=null}return e.prototype.execute=function(e){return r(this,void 0,void 0,function(){return a(this,function(t){return[2,this.recaptcha.execute(this.recaptchaID,{action:e})]})})},e.prototype.getSiteKey=function(){return this.siteKey},e.prototype.hideBadge=function(){null===this.styleContainer&&(this.styleContainer=document.createElement("style"),this.styleContainer.innerHTML=".grecaptcha-badge{display:none !important;}",document.head.appendChild(this.styleContainer))},e.prototype.showBadge=function(){null!==this.styleContainer&&(document.head.removeChild(this.styleContainer),this.styleContainer=null)},e}();t.ReCaptchaInstance=i},cxj3:function(e,t,n){"use strict";Object.defineProperty(t,"__esModule",{value:!0});var r=n("Xxa5"),a=n.n(r),i=n("exGp"),o=n.n(i),s=n("Dd8w"),c=n.n(s),u=n("NYxO"),l=n("+cKO"),d=n("TYx6"),f=n("i53X"),p=n("/QaM"),m=n("znC5"),h=n("a2KH"),v={name:"ConfirmField",props:{value:{type:Boolean,default:""},v:{type:Object,required:!0},id:{type:String,required:!0}},computed:{confirm:{get:function(){return this.value},set:function(e){this.$emit("input",e)}}}},g={render:function(){var e=this,t=e.$createElement,n=e._self._c||t;return n("div",{staticClass:"form__group"},[n("input",{directives:[{name:"model",rawName:"v-model",value:e.confirm,expression:"confirm"}],staticClass:"form__checkbox",class:{invalid:e.v.$dirty&&!e.v.sameAs},attrs:{type:"checkbox",name:"confirm",id:e.id},domProps:{checked:Array.isArray(e.confirm)?e._i(e.confirm,null)>-1:e.confirm},on:{change:function(t){var n=e.confirm,r=t.target,a=!!r.checked;if(Array.isArray(n)){var i=e._i(n,null);r.checked?i<0&&(e.confirm=n.concat([null])):i>-1&&(e.confirm=n.slice(0,i).concat(n.slice(i+1)))}else e.confirm=a}}}),n("label",{staticClass:"form__checkbox-label",attrs:{for:e.id}},[e._v("Я согласен с "),n("a",{attrs:{href:"#"}},[e._v("полит. конф-ти ")]),e._v("и передачей "),n("a",{attrs:{href:"#"}},[e._v("перс. данных")])])])},staticRenderFns:[]},y=n("VU/8")(v,g,!1,null,null,null).exports,b=n("oHEs"),w={name:"Registration",components:{PasswordField:d.a,EmailField:p.a,NameField:m.a,NumberField:h.a,ConfirmField:y,PasswordRepeatField:f.a,VueReCaptcha:b.VueReCaptcha},data:function(){return{email:"",passwd1:"",passwd2:"",firstName:"",lastName:"",confirm:!1,siteKey:"6LfacNYZAAAAAIMnVdbxt9DZBs45Yq3BBY_7yQZ1"}},computed:c()({},Object(u.c)("auth/api",["validCaptcha"])),methods:c()({},Object(u.b)("auth/api",["register","login","confirmCaptcha"]),{submitHandler:function(){var e=this;return o()(a.a.mark(function t(){return a.a.wrap(function(t){for(;;)switch(t.prev=t.next){case 0:if(!e.$v.$invalid){t.next=3;break}return e.$v.$touch(),t.abrupt("return");case 3:e.recaptcha(),setTimeout(function(){if(e.validCaptcha){var t=e.email,n=e.passwd1,r=e.passwd2,a=e.firstName,i=e.lastName;e.register({email:t,passwd1:n,passwd2:r,firstName:a,lastName:i}).then(function(){e.$router.push({name:"RegisterSuccess"})})}},1e3);case 5:case"end":return t.stop()}},t,e)}))()},recaptcha:function(){var e=this;grecaptcha.ready(function(){grecaptcha.execute(e.siteKey,{action:"register"}).then(function(t){e.confirmCaptcha(t)})})}}),validations:{email:{required:l.required,email:l.email},passwd1:{required:l.required,minLength:Object(l.minLength)(8)},passwd2:{required:l.required,minLength:Object(l.minLength)(8),sameAsPassword:Object(l.sameAs)("passwd1")},firstName:{required:l.required,minLength:Object(l.minLength)(3),maxLength:Object(l.maxLength)(25)},lastName:{required:l.required,minLength:Object(l.minLength)(3),maxLength:Object(l.maxLength)(25)}}},_={render:function(){var e=this,t=e.$createElement,n=e._self._c||t;return n("div",{staticClass:"registration"},[n("form",{staticClass:"registration__form",on:{submit:function(t){return t.preventDefault(),e.submitHandler(t)}}},[n("div",{staticClass:"form__block"},[n("h4",{staticClass:"form__subtitle"},[e._v("Аккаунт")]),n("email-field",{class:{checked:e.$v.email.required&&e.$v.email.email},attrs:{id:"register-email",v:e.$v.email},model:{value:e.email,callback:function(t){e.email=t},expression:"email"}}),n("password-field",{class:{checked:e.$v.passwd1.required&&e.$v.passwd2.sameAsPassword&&e.$v.passwd1.minLength},attrs:{id:"register-password",v:e.$v.passwd1,info:"info",registration:"registration"},model:{value:e.passwd1,callback:function(t){e.passwd1=t},expression:"passwd1"}}),n("password-repeat-field",{class:{checked:e.$v.passwd1.required&&e.$v.passwd2.sameAsPassword&&e.$v.passwd2.minLength},attrs:{id:"register-repeat-password",v:e.$v.passwd2},model:{value:e.passwd2,callback:function(t){e.passwd2=t},expression:"passwd2"}})],1),n("div",{staticClass:"form__block"},[n("h4",{staticClass:"form__subtitle"},[e._v("Личные данные")]),n("name-field",{attrs:{id:"register-firstName",v:e.$v.firstName},model:{value:e.firstName,callback:function(t){e.firstName=t},expression:"firstName"}}),n("name-field",{attrs:{id:"register-lastName",v:e.$v.lastName,label:"Фамилия"},model:{value:e.lastName,callback:function(t){e.lastName=t},expression:"lastName"}})],1),n("div",{staticClass:"registration__action"},[n("button-hover",{attrs:{tag:"button",type:"submit",variant:"white"}},[e._v("Зарегистрироваться")])],1)])])},staticRenderFns:[]};var L=n("VU/8")(w,_,!1,function(e){n("H6Pi")},null,null);t.default=L.exports},oHEs:function(e,t,n){"use strict";var r=this&&this.__awaiter||function(e,t,n,r){return new(n||(n=Promise))(function(a,i){function o(e){try{c(r.next(e))}catch(e){i(e)}}function s(e){try{c(r.throw(e))}catch(e){i(e)}}function c(e){var t;e.done?a(e.value):(t=e.value,t instanceof n?t:new n(function(e){e(t)})).then(o,s)}c((r=r.apply(e,t||[])).next())})},a=this&&this.__generator||function(e,t){var n,r,a,i,o={label:0,sent:function(){if(1&a[0])throw a[1];return a[1]},trys:[],ops:[]};return i={next:s(0),throw:s(1),return:s(2)},"function"==typeof Symbol&&(i[Symbol.iterator]=function(){return this}),i;function s(i){return function(s){return function(i){if(n)throw new TypeError("Generator is already executing.");for(;o;)try{if(n=1,r&&(a=2&i[0]?r.return:i[0]?r.throw||((a=r.return)&&a.call(r),0):r.next)&&!(a=a.call(r,i[1])).done)return a;switch(r=0,a&&(i=[2&i[0],a.value]),i[0]){case 0:case 1:a=i;break;case 4:return o.label++,{value:i[1],done:!1};case 5:o.label++,r=i[1],i=[0];continue;case 7:i=o.ops.pop(),o.trys.pop();continue;default:if(!(a=(a=o.trys).length>0&&a[a.length-1])&&(6===i[0]||2===i[0])){o=0;continue}if(3===i[0]&&(!a||i[1]>a[0]&&i[1]<a[3])){o.label=i[1];break}if(6===i[0]&&o.label<a[1]){o.label=a[1],a=i;break}if(a&&o.label<a[2]){o.label=a[2],o.ops.push(i);break}a[2]&&o.ops.pop(),o.trys.pop();continue}i=t.call(e,o)}catch(e){i=[6,e],r=0}finally{n=a=0}if(5&i[0])throw i[1];return{value:i[0]?i[1]:void 0,done:!0}}([i,s])}}};Object.defineProperty(t,"__esModule",{value:!0});var i=n("LcYm");t.VueReCaptcha=function(e,t){var n=this,i=new o,s=!1,c=null,u=[];e.prototype.$recaptchaLoaded=function(){return new Promise(function(e,t){return null!==c?t(c):!0===s?e(!0):void u.push({resolve:e,reject:t})})},i.initializeReCaptcha(t).then(function(t){s=!0,e.prototype.$recaptcha=function(e){return r(n,void 0,void 0,function(){return a(this,function(n){return[2,t.execute(e)]})})},e.prototype.$recaptchaInstance=t,u.forEach(function(e){return e.resolve(!0)})}).catch(function(e){c=e,u.forEach(function(t){return t.reject(e)})})};var o=function(){function e(){}return e.prototype.initializeReCaptcha=function(e){return r(this,void 0,void 0,function(){return a(this,function(t){return[2,i.load(e.siteKey,e.loaderOptions)]})})},e}()},tma6:function(e,t,n){"use strict";Object.defineProperty(t,"__esModule",{value:!0});var r,a=n("LztK");!function(e){e[e.NOT_LOADED=0]="NOT_LOADED",e[e.LOADING=1]="LOADING",e[e.LOADED=2]="LOADED"}(r||(r={}));var i=function(){function e(){}return e.load=function(t,n){if(void 0===n&&(n={}),"undefined"==typeof document)return Promise.reject(new Error("This is a library for the browser!"));if(e.getLoadingState()===r.LOADED)return e.instance.getSiteKey()===t?Promise.resolve(e.instance):Promise.reject(new Error("reCAPTCHA already loaded with different site key!"));if(e.getLoadingState()===r.LOADING)return t!==e.instanceSiteKey?Promise.reject(new Error("reCAPTCHA already loaded with different site key!")):new Promise(function(t,n){e.successfulLoadingConsumers.push(function(e){return t(e)}),e.errorLoadingRunnable.push(function(e){return n(e)})});e.instanceSiteKey=t,e.setLoadingState(r.LOADING);var i=new e;return new Promise(function(o,s){i.loadScript(t,n.useRecaptchaNet||!1,n.renderParameters?n.renderParameters:{},n.customUrl).then(function(){e.setLoadingState(r.LOADED);var s=i.doExplicitRender(grecaptcha,t,n.explicitRenderParameters?n.explicitRenderParameters:{}),c=new a.ReCaptchaInstance(t,s,grecaptcha);e.successfulLoadingConsumers.forEach(function(e){return e(c)}),e.successfulLoadingConsumers=[],n.autoHideBadge&&c.hideBadge(),e.instance=c,o(c)}).catch(function(t){e.errorLoadingRunnable.forEach(function(e){return e(t)}),e.errorLoadingRunnable=[],s(t)})})},e.getInstance=function(){return e.instance},e.setLoadingState=function(t){e.loadingState=t},e.getLoadingState=function(){return null===e.loadingState?r.NOT_LOADED:e.loadingState},e.prototype.loadScript=function(t,n,a,i){var o=this;void 0===n&&(n=!1),void 0===a&&(a={}),void 0===i&&(i="");var s=document.createElement("script");s.setAttribute("recaptcha-v3-script","");var c="https://www.google.com/recaptcha/api.js";n&&(c="https://recaptcha.net/recaptcha/api.js"),i&&(c=i),a.render&&(a.render=void 0);var u=this.buildQueryString(a);return s.src=c+"?render=explicit"+u,new Promise(function(t,n){s.addEventListener("load",o.waitForScriptToLoad(function(){t(s)}),!1),s.onerror=function(t){e.setLoadingState(r.NOT_LOADED),n(t)},document.head.appendChild(s)})},e.prototype.buildQueryString=function(e){return Object.keys(e).length<1?"":"&"+Object.keys(e).filter(function(t){return!!e[t]}).map(function(t){return t+"="+e[t]}).join("&")},e.prototype.waitForScriptToLoad=function(t){var n=this;return function(){void 0===window.grecaptcha?setTimeout(function(){n.waitForScriptToLoad(t)},e.SCRIPT_LOAD_DELAY):window.grecaptcha.ready(function(){t()})}},e.prototype.doExplicitRender=function(e,t,n){var r={sitekey:t,badge:n.badge,size:n.size,tabindex:n.tabindex};return n.container?e.render(n.container,r):e.render(r)},e.loadingState=null,e.instance=null,e.instanceSiteKey=null,e.successfulLoadingConsumers=[],e.errorLoadingRunnable=[],e.SCRIPT_LOAD_DELAY=25,e}();t.load=i.load,t.getInstance=i.getInstance},znC5:function(e,t,n){"use strict";var r={name:"EmailField",props:{value:{type:String,default:""},v:{type:Object,required:!0},label:{type:String,default:"Имя"},id:{type:String,required:!0}},computed:{name:{get:function(){return this.value},set:function(e){this.$emit("input",e)}}}},a={render:function(){var e=this,t=e.$createElement,n=e._self._c||t;return n("div",{staticClass:"form__group",class:{fill:e.name.length>0}},[n("input",{directives:[{name:"model",rawName:"v-model",value:e.name,expression:"name"}],staticClass:"form__input",class:{invalid:e.v.$dirty&&!e.v.required||e.v.$dirty&&!e.v.minLength},attrs:{id:e.id,name:"name"},domProps:{value:e.name},on:{change:function(t){return e.v.$touch()},input:function(t){t.target.composing||(e.name=t.target.value)}}}),n("label",{staticClass:"form__label",attrs:{for:e.id}},[e._v(e._s(e.label))]),e.v.$dirty&&!e.v.required?n("span",{staticClass:"form__error"},[e._v("Обязательно поле")]):e.v.$dirty&&!e.v.minLength?n("span",{staticClass:"form__error"},[e._v("Введено слишком мало символов")]):e.v.$dirty&&!e.v.maxLength?n("span",{staticClass:"form__error"},[e._v("Превышено максимальное количество символов")]):e._e()])},staticRenderFns:[]},i=n("VU/8")(r,a,!1,null,null,null);t.a=i.exports}});
//# sourceMappingURL=4.68162b01988619c6eda2.js.map