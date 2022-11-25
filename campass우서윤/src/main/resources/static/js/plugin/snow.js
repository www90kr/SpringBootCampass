window.onload = function(){
    var oSnow = new SnowCan({num:'50'});
    // var iDiv = document.getElementById('iDiv');
    // var aInput = iDiv.getElementsByTagName('input');


}
function SnowCan(oJson){
    var oJson = oJson || {},
        oC = document.getElementById(oJson.id) || document.getElementById('snow');
    oC.width = innerWidth;
    oC.height = innerHeight;
    this.oCG = oC.getContext('2d');
    this.oCG.clipContent= false;
    this.num = oJson.num || 100;
    this.aColor = oJson.aColor || ['#fff','#eee'];
    this.GLSpeed = oJson.GLspeed || 0;
    this.aSnowW = [1,2,3];
    this.docW = oJson.docW || oC.width;
    this.docH = oJson.docH || oC.height;
    this.timer = null;
    this.jPo = {
        x : [],
        y : []
    };
    this.jSp = {
        x : [1,0.5,1.2,-1,-0.5],
        y : [1.5,2.5,3.8,3.5]
    };
    this.init();
    var _this = this;
    this.timer = setInterval(function(){
        _this.oCG.clearRect(0,0,_this.docW, _this.docH);
        _this.creatArc();

    },1000/200);
}
SnowCan.prototype.init = function(){

    this.jPo.x = toRandom(-this.docW, this.docW, this.num);
    this.jPo.y = toRandom(-this.docH, this.docH, this.num);
    this.oCG.fillStyle = this.aColor[0];
    this.creatArc();
    console.log(this.jPo)
}
SnowCan.prototype.creatArc = function(){

    for( var i = 0; i< this.num ; i++){

        this.oCG.beginPath();
        this.oCG.arc(this.jPo.x[i], this.jPo.y[i], this.aSnowW[i%this.aSnowW.length], 0 ,2*Math.PI);
        this.oCG.closePath();
        this.oCG.fill();
        this.jPo.x[i]+= this.jSp.x[i%this.jSp.x.length]/2 ;
        this.jPo.y[i]+= this.jSp.y[i%this.jSp.y.length]/2;
        if(this.jPo.x[i] > this.docW + 10 || this.jPo.x[i] < 0 - 10 || this.jPo.y[i] > this.docH + 10){
            this.jPo.x[i] = getRandom(0, this.docW);
            this.jPo.y[i] = getRandom(-100, 0);
        }

    }
}

function toRandom(iMin,iMax,iNum){
    var json={};
    var arr=[];
    while(arr.length<iNum)
    {
        var rnum=Math.floor(Math.random()*(iMax-iMin))+iMin;
        if(!json[rnum])
        {
            json[rnum]=rnum;
            arr.push(rnum);
        }
    }
    return arr;
}

function getRandom(iMin,iMax){
    return Math.round(Math.random()*(iMax-iMin))+iMin;
}