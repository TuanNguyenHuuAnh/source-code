(function(){var UH5=function(){if(this.JZk){this.JZk();return;}var qk=UH5.prototype;qk.JZk=function(){this.ikv="";this.SGA="";};qk.setServerURL=function(dq){
this.ikv=dq;};qk.setServerIP=function(lBg){this.SGA=lBg;};qk.createSecureOutputStream=function(nv,gy){var P8k=new ByteArray();var Ag=0;var Ctk;
for(Ctk in gy){Ag++;}P8k.writeInt(Ag);for(Ctk in gy){var jvz=gy[Ctk];this.XU(P8k,Ctk);this.XU(P8k,jvz);}var UqX=new ByteArray();var nkU=new this.JhA("forcs@#$",UqX);
nkU.write(nv,0,nv.length);P8k.writeBytes(UqX,0,UqX.length);P8k.position=0;return P8k;};qk.createSecureInputStream=function(nv,gy){var size=nv.readInt();
for(var i=0; i<size; i++){var Ctk=this.rO(nv);var jvz=this.rO(nv);gy[Ctk]=jvz;}var P8k=new ByteArray();var ApA=new this.phA("forcs@#$",nv);var eiU=new ByteArray();
ApA.fG(eiU,0,nv.length-nv.position);P8k.writeBytes(eiU,0,eiU.length);P8k.position=0;return P8k;};qk.XU=function(nv,fF){var i;var r0k=fF.length;
nv.writeInt(r0k);var v;for(i=0; i<r0k; i++){v=fF.charCodeAt(i);nv.writeByte((v>>>8)&255);nv.writeByte((v>>>0)&255);}};qk.rO=function(nv){var r0k;
var mrg,oCg;r0k=nv.readInt();if(r0k==-1){return "<NULL>";}else{if(r0k<-1){throw new Error("A malformed string has been read in a data input stream.");
}}var fF="";var position=nv.position;for(var i=0; i<r0k; i++){mrg=nv[position+i*2];oCg=nv[position+i*2+1];if((mrg|oCg)<0){throw new Error("A malformed string has been read in a data input stream.");
}fF+=String.fromCharCode((mrg<<8)+(oCg<<0));}nv.position+=r0k*2;return fF;};var phA=function(fb,T4E){if(this.p6K){this.p6K(fb,T4E);return;}var vOk=phA.prototype;
vOk.p6K=function(fb,T4E){this.TSQ=fb;this.ax=0;this.bx=0;this.cx=0;this.dx=0;this.si=0;this.hF=0;this.RtA=0;this.G5k=0;this.i=0;this.tXk=0;this.a6k=new Array();
this.J9X=0;this.S9X=0;this.maF=0;this.Up=T4E;this.ifk=new ByteArray();this.ifk.setLength(17);var cEk=new ByteArray();cEk.writeMultiByte(fb,"iso-8859-1");
this.ifk.writeBytes(cEk,0,cEk.length>16?16:cEk.length);this.ifk.set(16,0);this.clear();};vOk.clear=function(){this.ax=0;this.bx=0;this.cx=0;this.dx=0;
this.si=0;this.hF=0;this.RtA=0;this.G5k=0;this.i=0;this.tXk=0;this.J9X=0;this.S9X=0;this.maF=0;for(var i=0; i<8; i++){this.a6k[i]=0;}};vOk.nEX=function(){
var c=this.Up.readByte();if(c==-1){return -1;}this.pAA();this.J9X=this.tXk>>>8;this.S9X=this.tXk&255;c=c^(this.J9X^this.S9X);for(this.maF=0; this.maF<=15; this.maF++){
this.ifk.set(this.maF,this.ifk.get(this.maF)^c);}return c;};vOk.fG=function(b,off,Fg,kkz){if(b===undefined){b=null;}if(off===undefined){off=-1;
}if(Fg===undefined){Fg=-1;}if(kkz===undefined){kkz=-1;}if((b==null)||this.Up==null){Be("Null point exception");return -1;}if(Fg<1){return 0;}
this.Up.readBytes(b,off,Fg);var rt=Fg;if(rt<=0){return rt;}var c=0;var i=0;for(i=0; i<rt; i++){this.pAA();this.J9X=this.tXk>>>8;this.S9X=this.tXk&255;
c=b.get(i+off);c=c^(this.J9X^this.S9X);for(var j=0; j<16; j++){this.ifk.set(j,this.ifk.get(j)^c);}b.set(i+off,c);}return rt;};vOk.Vy=function(){
return 0;};vOk.iGz=function(){this.dx=this.RtA+this.i;this.ax=this.a6k[this.i];this.cx=346;this.bx=20021;this.hF=this.ax;this.ax=this.si;this.si=this.hF;
this.hF=this.ax;this.ax=this.dx;this.dx=this.hF;this.ax=this.ax*this.bx&65535;this.hF=this.ax;this.ax=this.cx;this.cx=this.hF;if(this.ax!=0){
this.ax=(this.ax*this.si)&65535;this.cx=(this.ax+this.cx)&65535;}this.hF=this.ax;this.ax=this.si;this.si=this.hF;this.ax=(this.ax*this.bx)&65535;
this.dx=(this.cx+this.dx)&65535;this.ax=this.ax+1;this.RtA=this.dx;this.a6k[this.i]=this.ax;this.G5k=this.ax^this.dx;this.i=this.i+1;};vOk.pAA=function(){
this.a6k[0]=(this.ifk.get(0)*256)+this.ifk.get(1);this.iGz();this.tXk=this.G5k;this.a6k[1]=this.a6k[0]^((this.ifk.get(2)*256)+this.ifk.get(3));
this.iGz();this.tXk=this.tXk^this.G5k;this.a6k[2]=this.a6k[1]^((this.ifk.get(4)*256)+this.ifk.get(5));this.iGz();this.tXk=this.tXk^this.G5k;this.a6k[3]=this.a6k[2]^((this.ifk.get(6)*256)+this.ifk.get(7));
this.iGz();this.tXk=this.tXk^this.G5k;this.a6k[4]=this.a6k[3]^((this.ifk.get(8)*256)+this.ifk.get(9));this.iGz();this.tXk=this.tXk^this.G5k;this.a6k[5]=this.a6k[4]^((this.ifk.get(10)*256)+this.ifk.get(11));
this.iGz();this.tXk=this.tXk^this.G5k;this.a6k[6]=this.a6k[5]^((this.ifk.get(12)*256)+this.ifk.get(13));this.iGz();this.tXk=this.tXk^this.G5k;
this.a6k[7]=this.a6k[6]^((this.ifk.get(14)*256)+this.ifk.get(15));this.iGz();this.tXk=this.tXk^this.G5k;this.i=0;};this.p6K(fb,T4E);};qk.phA=phA;
var JhA=function(fb,HxO){if(this.J6K){this.J6K(fb,HxO);return;}var vOk=JhA.prototype;vOk.J6K=function(fb,HxO){this.ax=0;this.bx=0;this.cx=0;this.dx=0;
this.si=0;this.hF=0;this.RtA=0;this.G5k=0;this.i=0;this.tXk=0;this.a6k=new Array();this.J9X=0;this.S9X=0;this.maF=0;this.mW=HxO;this.a6k.length=8;
this.ifk=new ByteArray();this.ifk.setLength(17);var cEk=new ByteArray();cEk.writeMultiByte(fb,"iso-8859-1");this.ifk.writeBytes(cEk,0,cEk.length>16?16:cEk.length);
this.ifk.set(16,0);this.clear();};vOk.clear=function(){this.ax=0;this.bx=0;this.cx=0;this.dx=0;this.si=0;this.hF=0;this.RtA=0;this.G5k=0;this.i=0;
this.tXk=0;this.J9X=0;this.S9X=0;this.maF=0;for(var i=0; i<8; i++){this.a6k[i]=0;}};vOk.fEk=function(b){this.pAA();this.J9X=this.tXk>>>8;this.S9X=this.tXk&255;
for(this.maF=0; this.maF<=15; this.maF++){this.ifk.set(this.maF,this.ifk.get(this.maF)^b);}b=b^(this.J9X^this.S9X);this.mW.writeByte(b);};vOk.write=function(b,off,Fg){
if(b===undefined){b=null;}if(off===undefined){off=-1;}if(Fg===undefined){Fg=-1;}if((b==null)||this.mW==null){Be("Null point exception");return;
}if(Fg<1){return;}var c=0;var qgA=new ByteArray();qgA.setLength(Fg);for(var i=0; i<Fg; i++){this.pAA();this.J9X=this.tXk>>>8;this.S9X=this.tXk&255;
c=b.get(i+off);for(var j=0; j<16; j++){this.ifk.set(j,this.ifk.get(j)^c);}c=c^(this.J9X^this.S9X);qgA.set(i,c);}this.mW.writeBytes(qgA,0,Fg);
qgA=null;};vOk.flush=function(){};vOk.close=function(){};vOk.iGz=function(){this.dx=this.RtA+this.i;this.ax=this.a6k[this.i];this.cx=346;this.bx=20021;
this.hF=this.ax;this.ax=this.si;this.si=this.hF;this.hF=this.ax;this.ax=this.dx;this.dx=this.hF;this.ax=this.ax*this.bx&65535;this.hF=this.ax;
this.ax=this.cx;this.cx=this.hF;if(this.ax!=0){this.ax=(this.ax*this.si)&65535;this.cx=(this.ax+this.cx)&65535;}this.hF=this.ax;this.ax=this.si;
this.si=this.hF;this.ax=(this.ax*this.bx)&65535;this.dx=(this.cx+this.dx)&65535;this.ax=this.ax+1;this.RtA=this.dx;this.a6k[this.i]=this.ax;this.G5k=this.ax^this.dx;
this.i=this.i+1;};vOk.pAA=function(){this.a6k[0]=(this.ifk.get(0)*256)+this.ifk.get(1);this.iGz();this.tXk=this.G5k;this.a6k[1]=this.a6k[0]^((this.ifk.get(2)*256)+this.ifk.get(3));
this.iGz();this.tXk=this.tXk^this.G5k;this.a6k[2]=this.a6k[1]^((this.ifk.get(4)*256)+this.ifk.get(5));this.iGz();this.tXk=this.tXk^this.G5k;this.a6k[3]=this.a6k[2]^((this.ifk.get(6)*256)+this.ifk.get(7));
this.iGz();this.tXk=this.tXk^this.G5k;this.a6k[4]=this.a6k[3]^((this.ifk.get(8)*256)+this.ifk.get(9));this.iGz();this.tXk=this.tXk^this.G5k;this.a6k[5]=this.a6k[4]^((this.ifk.get(10)*256)+this.ifk.get(11));
this.iGz();this.tXk=this.tXk^this.G5k;this.a6k[6]=this.a6k[5]^((this.ifk.get(12)*256)+this.ifk.get(13));this.iGz();this.tXk=this.tXk^this.G5k;
this.a6k[7]=this.a6k[6]^((this.ifk.get(14)*256)+this.ifk.get(15));this.iGz();this.tXk=this.tXk^this.G5k;this.i=0;};this.J6K(fb,HxO);};qk.JhA=JhA;
this.JZk();};return UH5;})();
