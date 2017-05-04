"use strict";(function(a){if(typeof define==="function"&&define.amd){define(["jquery"],a)}else{if(typeof exports==="object"){module.exports=a(require("jquery"))}else{a(jQuery||Zepto)}}}(function(c){var e=function(j,g,i){j=c(j);var k=this,h=j.val(),f;g=typeof g==="function"?g(j.val(),undefined,j,i):g;var l={invalid:[],getCaret:function(){try{var o,r=0,n=j.get(0),m=document.selection,q=n.selectionStart;if(m&&navigator.appVersion.indexOf("MSIE 10")===-1){o=m.createRange();o.moveStart("character",j.is("input")?-j.val().length:-j.text().length);r=o.text.length}else{if(q||q==="0"){r=q}}return r}catch(p){}},setCaret:function(p){try{if(j.is(":focus")){var m,n=j.get(0);if(n.setSelectionRange){n.setSelectionRange(p,p)}else{if(n.createTextRange){m=n.createTextRange();m.collapse(true);m.moveEnd("character",p);m.moveStart("character",p);m.select()}}}}catch(o){}},events:function(){j.on("keyup.mask",l.behaviour).on("paste.mask drop.mask",function(){setTimeout(function(){j.keydown().keyup()},100)}).on("change.mask",function(){j.data("changed",true)}).on("blur.mask",function(){if(h!==j.val()&&!j.data("changed")){j.triggerHandler("change")}j.data("changed",false)}).on("keydown.mask, blur.mask",function(){h=j.val()}).on("focus.mask",function(m){if(i.selectOnFocus===true){c(m.target).select()}}).on("focusout.mask",function(){if(i.clearIfNotMatch&&!f.test(l.val())){l.val("")}})},getRegexMask:function(){var o=[],u,t,n,m,s,q;for(var p=0;p<g.length;p++){u=k.translation[g.charAt(p)];if(u){t=u.pattern.toString().replace(/.{1}$|^.{1}/g,"");n=u.optional;m=u.recursive;if(m){o.push(g.charAt(p));s={digit:g.charAt(p),pattern:t}}else{o.push(!n&&!m?t:(t+"?"))}}else{o.push(g.charAt(p).replace(/[-\/\\^$*+?.()|[\]{}]/g,"\\$&"))}}q=o.join("");if(s){q=q.replace(new RegExp("("+s.digit+"(.*"+s.digit+")?)"),"($1)?").replace(new RegExp(s.digit,"g"),s.pattern)}return new RegExp(q)},destroyEvents:function(){j.off(["keydown","keyup","paste","drop","blur","focusout",""].join(".mask "))},val:function(m){var n=j.is("input"),p=n?"val":"text",o;if(arguments.length>0){if(j[p]()!==m){j[p](m)}o=j}else{o=j[p]()}return o},getMCharsBeforeCount:function(n,m){for(var p=0,o=0,q=g.length;o<q&&o<n;o++){if(!k.translation[g.charAt(o)]){n=m?n+1:n;p++}}return p},caretPos:function(q,m,n,p){var o=k.translation[g.charAt(Math.min(q-1,g.length-1))];return !o?l.caretPos(q+1,m,n,p):Math.min(q+n-m-p,n)},behaviour:function(q){q=q||window.event;l.invalid=[];var t=q.keyCode||q.which;if(c.inArray(t,k.byPassKeys)===-1){var p=l.getCaret(),n=l.val(),r=n.length,o=p<r,m=l.getMasked(),u=m.length,s=l.getMCharsBeforeCount(u-1)-l.getMCharsBeforeCount(r-1);l.val(m);if(o&&!(t===65&&q.ctrlKey)){if(!(t===8||t===46)){p=l.caretPos(p,r,u,s)}l.setCaret(p)}return l.callbacks(q)}},getMasked:function(A){var r=[],B=l.val(),t=0,y=g.length,C=0,q=B.length,x=1,w="push",s=-1,D,p;if(i.reverse){w="unshift";x=-1;D=0;t=y-1;C=q-1;p=function(){return t>-1&&C>-1}}else{D=y-1;p=function(){return t<y&&C<q}}while(p()){var u=g.charAt(t),o=B.charAt(C),n=k.translation[u];if(n){if(o.match(n.pattern)){r[w](o);if(n.recursive){if(s===-1){s=t}else{if(t===D){t=s-x}}if(D===s){t-=x}}t+=x}else{if(n.optional){t+=x;C-=x}else{if(n.fallback){r[w](n.fallback);t+=x;C-=x}else{l.invalid.push({p:C,v:o,e:n.pattern})}}}C+=x}else{if(!A){r[w](u)}if(o===u){C+=x}t+=x}}var z=g.charAt(D);if(y===q+1&&!k.translation[z]){r.push(z)}return r.join("")},callbacks:function(n){var p=l.val(),o=p!==h,m=[p,n,j,i],q=function(s,t,r){if(typeof i[s]==="function"&&t){i[s].apply(this,r)}};q("onChange",o===true,m);q("onKeyPress",o===true,m);q("onComplete",p.length===g.length,m);q("onInvalid",l.invalid.length>0,[p,n,j,l.invalid,i])}};k.mask=g;k.options=i;k.remove=function(){var m=l.getCaret();l.destroyEvents();l.val(k.getCleanVal());l.setCaret(m-l.getMCharsBeforeCount(m));return j};k.getCleanVal=function(){return l.getMasked(true)};k.init=function(m){m=m||false;i=i||{};k.byPassKeys=c.jMaskGlobals.byPassKeys;k.translation=c.jMaskGlobals.translation;k.translation=c.extend({},k.translation,i.translation);k=c.extend(true,{},k,i);f=l.getRegexMask();if(m===false){if(i.placeholder){j.attr("placeholder",i.placeholder)}j.attr("autocomplete","off");l.destroyEvents();l.events();var n=l.getCaret();l.val(l.getMasked());l.setCaret(n+l.getMCharsBeforeCount(n,true))}else{l.events();l.val(l.getMasked())}};k.init(!j.is("input"))};c.maskWatchers={};var d=function(){var g=c(this),h={},i="data-mask-",f=g.attr("data-mask");if(g.attr(i+"reverse")){h.reverse=true}if(g.attr(i+"clearifnotmatch")){h.clearIfNotMatch=true}if(g.attr(i+"selectonfocus")==="true"){h.selectOnFocus=true}if(a(g,f,h)){return g.data("mask",new e(this,f,h))}},a=function(j,f,g){g=g||{};var k=c(j).data("mask"),l=JSON.stringify,h=c(j).val()||c(j).text();try{if(typeof f==="function"){f=f(h)}return typeof k!=="object"||l(k.options)!==l(g)||k.mask!==f}catch(i){}};c.fn.mask=function(g,j){j=j||{};var f=this.selector,k=c.jMaskGlobals,i=c.jMaskGlobals.watchInterval,h=function(){if(a(this,g,j)){return c(this).data("mask",new e(this,g,j))}};c(this).each(h);if(f&&f!==""&&k.watchInputs){clearInterval(c.maskWatchers[f]);c.maskWatchers[f]=setInterval(function(){c(document).find(f).each(h)},i)}return this};c.fn.unmask=function(){clearInterval(c.maskWatchers[this.selector]);delete c.maskWatchers[this.selector];return this.each(function(){var f=c(this).data("mask");if(f){f.remove().removeData("mask")}})};c.fn.cleanVal=function(){return this.data("mask").getCleanVal()};c.applyDataMask=function(f){f=f||c.jMaskGlobals.maskElements;var g=(f instanceof c)?f:c(f);g.filter(c.jMaskGlobals.dataMaskAttr).each(d)};var b={maskElements:"input,td,span,div",dataMaskAttr:"*[data-mask]",dataMask:true,watchInterval:300,watchInputs:true,watchDataMask:false,byPassKeys:[9,16,17,18,36,37,38,39,40,91],translation:{"0":{pattern:/\d/},"9":{pattern:/\d/,optional:true},"#":{pattern:/\d/,recursive:true},A:{pattern:/[a-zA-Z0-9]/},S:{pattern:/[a-zA-Z]/}}};c.jMaskGlobals=c.jMaskGlobals||{};b=c.jMaskGlobals=c.extend(true,{},b,c.jMaskGlobals);if(b.dataMask){c.applyDataMask()}setInterval(function(){if(c.jMaskGlobals.watchDataMask){c.applyDataMask()}},b.watchInterval)}));