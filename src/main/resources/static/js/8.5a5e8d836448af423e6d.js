webpackJsonp([8],{hHCC:function(e,t){},xoYT:function(e,t,a){"use strict";Object.defineProperty(t,"__esModule",{value:!0});var i=a("Dd8w"),s=a.n(i),n=a("NYxO"),r=a("+cKO"),l=a("/QaM"),o=a("znC5"),m={name:"ContactingSupport",components:{EmailField:l.a,NameField:o.a},data:function(){return{email:"",name:"",message:""}},methods:s()({},Object(n.b)("support/api",["sendMessage"]),{submitHandler:function(){var e=this;if(this.$v.$invalid)this.$v.$touch();else{var t=this.email,a=this.name,i=this.message;this.sendMessage({email:t,name:a,message:i}).then(function(){e.$router.push({name:"ContactingSupportSuccess"})})}}}),validations:{email:{required:r.required,email:r.email},name:{required:r.required,minLength:Object(r.minLength)(3),maxLength:Object(r.maxLength)(25)}}},u={render:function(){var e=this,t=e.$createElement,a=e._self._c||t;return a("div",{staticClass:"support-contacting"},[a("form",{staticClass:"support-contacting__form",on:{submit:function(t){return t.preventDefault(),e.submitHandler(t)}}},[a("div",{staticClass:"form__block"},[a("h4",{staticClass:"form__subtitle"},[e._v("Обратная связь")]),a("email-field",{class:{checked:e.$v.email.required&&e.$v.email.email},attrs:{id:"email",v:e.$v.email},model:{value:e.email,callback:function(t){e.email=t},expression:"email"}}),a("name-field",{attrs:{id:"firstName",v:e.$v.name},model:{value:e.name,callback:function(t){e.name=t},expression:"name"}})],1),a("div",{staticClass:"form__block"},[a("h4",{staticClass:"form__subtitle"},[e._v("Текст обращения")]),a("div",{staticClass:"support-contacting__wrap"},[a("textarea",{directives:[{name:"model",rawName:"v-model",value:e.message,expression:"message"}],staticClass:"support-contacting-text--textarea",attrs:{placeholder:"введите текст обращения"},domProps:{value:e.message},on:{input:function(t){t.target.composing||(e.message=t.target.value)}}})])]),a("div",{staticClass:"registration__action"},[a("button-hover",{attrs:{tag:"button",type:"submit",variant:"white"}},[e._v("Отправить")])],1)])])},staticRenderFns:[]};var c=a("VU/8")(m,u,!1,function(e){a("hHCC")},null,null);t.default=c.exports},znC5:function(e,t,a){"use strict";var i={name:"EmailField",props:{value:{type:String,default:""},v:{type:Object,required:!0},label:{type:String,default:"Имя"},id:{type:String,required:!0}},computed:{name:{get:function(){return this.value},set:function(e){this.$emit("input",e)}}}},s={render:function(){var e=this,t=e.$createElement,a=e._self._c||t;return a("div",{staticClass:"form__group",class:{fill:e.name.length>0}},[a("input",{directives:[{name:"model",rawName:"v-model",value:e.name,expression:"name"}],staticClass:"form__input",class:{invalid:e.v.$dirty&&!e.v.required||e.v.$dirty&&!e.v.minLength},attrs:{id:e.id,name:"name"},domProps:{value:e.name},on:{change:function(t){return e.v.$touch()},input:function(t){t.target.composing||(e.name=t.target.value)}}}),a("label",{staticClass:"form__label",attrs:{for:e.id}},[e._v(e._s(e.label))]),e.v.$dirty&&!e.v.required?a("span",{staticClass:"form__error"},[e._v("Обязательно поле")]):e.v.$dirty&&!e.v.minLength?a("span",{staticClass:"form__error"},[e._v("Введено слишком мало символов")]):e.v.$dirty&&!e.v.maxLength?a("span",{staticClass:"form__error"},[e._v("Превышено максимальное количество символов")]):e._e()])},staticRenderFns:[]},n=a("VU/8")(i,s,!1,null,null,null);t.a=n.exports}});
//# sourceMappingURL=8.5a5e8d836448af423e6d.js.map