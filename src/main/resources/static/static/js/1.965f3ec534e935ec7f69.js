webpackJsonp([1],{"3bEk":function(t,e){},RAiJ:function(t,e,n){"use strict";Object.defineProperty(e,"__esModule",{value:!0});var s={render:function(){var t=this,e=t.$createElement,n=t._self._c||e;return n("div",{staticStyle:{"text-align":"start"}},[n("h1",[t._v("索引列表如下：")]),t._v(" "),n("h4",[t._v("  每一项的第一个是词项，下面的是其倒排索引表")]),t._v(" "),n("ul",t._l(t.invertedIndex,function(e,s){return n("li",{key:s},[t._v("\n            "+t._s(s)+"  \n            "),t._l(e,function(e){return n("p",{key:e.personID+e.attrID},[t._v("DocID: "+t._s(e.personID)+"  attrID: "+t._s(e.attrID)+"  \n                startPos: "+t._s(e.startPos)+"  endPos: "+t._s(e.endPos)+"\n            ")])})],2)}))])},staticRenderFns:[]};var a=n("VU/8")({name:"",data:function(){return{invertedIndex:{}}},components:{},computed:{},mounted:function(){var t=this,e=this.$store.state.tempFile,n=e.destination.substring(0,e.destination.lastIndexOf("/"))+"/"+e.savename+".schs";this.axios.get("/upload/getInvertIndex",{params:{path:n}}).then(function(e){t.invertedIndex=e.data.content}).catch(function(e){t.$message("出现了一些错误，请刷新后重试！")})},methods:{}},s,!1,function(t){n("3bEk")},"data-v-f8f75142",null);e.default=a.exports}});
//# sourceMappingURL=1.965f3ec534e935ec7f69.js.map