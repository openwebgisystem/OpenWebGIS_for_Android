/*
  Copyright (c) 2015, OpenWebGIS, Fedor Kolomeyko <openwebgisnew@gmail.com>
All rights reserved.

Redistribution and use in source and binary forms, with or without modification, are
permitted provided that the following conditions are met:

  1. Redistributions of source code must retain the above copyright notice, this list of
     conditions and the following disclaimer.

  2. Redistributions in binary form must reproduce the above copyright notice, this list
     of conditions and the following disclaimer in the documentation and/or other materials
     provided with the distribution.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY
EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF
MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE
COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR
TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 -->
*/


function initColorpalOWG()
{var colorpalOWG='<div id="paletteCcolor"></div>'+'<div id="blocklegcolor"> .</div>'+
'<input size="8" title="insert color in hexadecimal format. For example: #00FFFF" type="text" id="blockCustColor_id" style="float:left;border: solid 1px black;" value="#0000FF"><button title="Insert color in hexadecimal format. For example: #00FFFF. Then press /Apply/, after that press button /OK/ to view the result " type=button onclick=OK_CustColor_idcol()><a style="font-size: 10px;">Apply</a></button><br>'+
'<div class="ok"> <button type=button onclick=OKcloseCol()>OK</button>'+
'</div><center><font size=+3>'+
'</font></center>'+
'</div>';
var body = document.body; var docElem = document.documentElement;
 var scrollTop = window.pageYOffset || docElem.scrollTop || body.scrollTop; var scrollLeft = window.pageXOffset || docElem.scrollLeft || body.scrollLeft
 var clientTop = docElem.clientTop || body.clientTop || 0; var clientLeft = docElem.clientLeft || body.clientLeft || 0
 var topTop = 0 + scrollTop - clientTop; var leftLeft =0 + scrollLeft - clientLeft
var DivEditeLeg=document.createElement("div");DivEditeLeg.id="id_colorPalOWG";
DivEditeLeg.style.width="400px";DivEditeLeg.style.height="200px";
DivEditeLeg.className="id_divRoutLegMain";DivEditeLeg.style.position="absolute";DivEditeLeg.style.display="block";DivEditeLeg.style.zIndex = 300000;DivEditeLeg.style.background="#ffffff";DivEditeLeg.style.border="2px solid black";DivEditeLeg.style.color="#000000";
DivEditeLeg.style.left=Math.round(document.body.clientWidth/2-400);DivEditeLeg.style.top=Math.round(document.body.clientHeight/2);
DivEditeLeg.innerHTML=colorpalOWG;
if(!document.getElementById("id_colorPalOWG"))
{body.appendChild(DivEditeLeg); 
document.getElementById("id_colorPalOWG").onmousedown=function(event){drags(event)};
document.getElementById("id_colorPalOWG").onmouseup=function(e){enddrag()};}
drawPalettecolor();
}

var colorElemWinp;
function OK_CustColor_idcol()
{document.getElementById("blocklegcolor").style.background=document.getElementById("blockCustColor_id").value;colorElemWinp=document.getElementById("blockCustColor_id").value; }

function OKcloseCol(){
//this.close(); 
//window.opener.LegendFieldMain=document.getElementById("pField").value;





if(colorElem.id=="colorColorMain")
{document.body.style.background=colorElemWinp;} 
else
{colorElem.parentNode.parentNode.style.background=colorElemWinp; }
document.getElementById("id_colorPalOWG").parentNode.removeChild(document.getElementById("id_colorPalOWG"));
}
 /////////////
function drawPalettecolor() {
	var out = "";
	for (var i = 0; i < 360; i++) {
		out += "<div onclick='selectColorcolor(this);' style='background-color:" + HSLToRGB(i, 100, 100) + "'><\/div>";
	}
		out += "<div onclick='selectColorcolor(this);' style='height:20px; float:left; background-color:#FFFFFF;width:2px;'><\/div>";
out += "<div onclick='selectColorcolor(this);' style='height:20px; float:left; background-color:#000000;width:2px;'><\/div>";
	document.getElementById("paletteCcolor").innerHTML = out;


}


function selectColorcolor(div) {
	colorElemWinp = div.style.backgroundColor;
	colorElemWinp = rgbNormal(colorElemWinp);
document.getElementById("blocklegcolor").style.background=colorElemWinp; 
}
function rgbNormal(color) {
	color = color.toString();
	var re = /rgb\((.*?)\)/i;
	if(re.test(color)) {
		compose = RegExp.$1.split(",");
		var hex = ['0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'];
		var result = "#";
		for (var i = 0; i < compose.length; i++) {
			rgb = parseInt(compose[i]);
			result += hex[parseInt(rgb / 16)] + hex[rgb % 16];
		}
		return result;
	} else return color;
}








///////////



