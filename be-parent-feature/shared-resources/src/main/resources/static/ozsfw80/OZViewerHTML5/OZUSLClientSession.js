(function(){var Xy_=function(){if(this.JZk){this.JZk();return;}var qk=Xy_.prototype;qk.JZk=function(){this.ikv="";this.SGA="";};qk.setServerURL=function(dq){
this.ikv=dq;};qk.setServerIP=function(lBg){this.SGA=lBg;};qk.createSecureOutputStream=function(nv,gy){var P8k=new ByteArray();var Ag=0;var Ctk;
for(Ctk in gy){Ag++;}P8k.writeInt(Ag);for(Ctk in gy){var jvz=gy[Ctk];this.XU(P8k,Ctk);this.XU(P8k,jvz);}P8k.writeBytes(nv,0,nv.length);P8k.position=0;
return P8k;};qk.createSecureInputStream=function(nv,gy){var size=nv.readInt();for(var i=0; i<size; i++){var Ctk=this.rO(nv);var jvz=this.rO(nv);
gy[Ctk]=jvz;}var P8k=new ByteArray();P8k.writeBytes(nv,nv.position,nv.length-nv.position);P8k.position=0;return P8k;};qk.XU=function(nv,fF){var i;
var r0k=fF.length;nv.writeInt(r0k);var v;for(i=0; i<r0k; i++){v=fF.charCodeAt(i);nv.writeByte((v>>>8)&255);nv.writeByte((v>>>0)&255);}};qk.rO=function(nv){
var r0k;var mrg,oCg;r0k=nv.readInt();if(r0k==-1){return "<NULL>";}else{if(r0k<-1){throw new Error("A malformed string has been read in a data input stream.");
}}var fF="";var position=nv.position;for(var i=0; i<r0k; i++){mrg=nv[position+i*2];oCg=nv[position+i*2+1];if((mrg|oCg)<0){throw new Error("A malformed string has been read in a data input stream.");
}fF+=String.fromCharCode((mrg<<8)+(oCg<<0));}nv.position+=r0k*2;return fF;};this.JZk();};return Xy_;})();
