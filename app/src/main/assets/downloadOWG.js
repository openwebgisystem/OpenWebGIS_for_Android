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


 */
var showSave;
function OKclosedownload()
{document.getElementById("id_downloadOWG").parentNode.removeChild(document.getElementById("id_downloadOWG"));}
function init_download(){

var body = document.body; var docElem = document.documentElement;
 var scrollTop = window.pageYOffset || docElem.scrollTop || body.scrollTop; var scrollLeft = window.pageXOffset || docElem.scrollLeft || body.scrollLeft
 var clientTop = docElem.clientTop || body.clientTop || 0; var clientLeft = docElem.clientLeft || body.clientLeft || 0
 var topTop = 0 + scrollTop - clientTop; var leftLeft =0 + scrollLeft - clientLeft
var DivEditeLeg=document.createElement("div");DivEditeLeg.id="id_downloadOWG";
DivEditeLeg.style.width=screen.width;DivEditeLeg.style.height=screen.height;
DivEditeLeg.className="id_divRoutLegMain";DivEditeLeg.style.position="absolute";DivEditeLeg.style.display="block";DivEditeLeg.style.zIndex = 300000;DivEditeLeg.style.background="#ffffff";DivEditeLeg.style.border="2px solid black";DivEditeLeg.style.color="#000000";
DivEditeLeg.style.left=Math.round(document.body.clientWidth/2-100);DivEditeLeg.style.top="0px";
DivEditeLeg.innerHTML='<label for="filename">File name:</label><input type="text" id="filename" value="Example.txt"/>'+
'<label for="mimetype">Mime type:</label><input type="text" id="mimetype" value="text/plain; charset=UTF-8"/><br/>'+
'<label for="data">File contents:</label><br/><textarea id="data" style="width: 1000px; height: 310px;">>Multiline text containing unicode characters</textarea>'+
'<br/><button onclick="saveData();">Save Data</button><p><button type=button onclick=OKclosedownload()>Close</button></p>';
if(!document.getElementById("id_downloadOWG"))
{body.appendChild(DivEditeLeg); 
document.getElementById("id_downloadOWG").onmousedown=function(event){drags(event)};
document.getElementById("id_downloadOWG").onmouseup=function(e){enddrag()};}
document.getElementById('data').value=kmlText2;if(namedownlodfileopenwebgis){document.getElementById('filename').value=namedownlodfileopenwebgis;var r;namedownlodfileopenwebgis=r;}


var DownloadAttributeSupport = 'download' in document.createElement('a');


var BlobBuilder = window.BlobBuilder || window.WebKitBlobBuilder || window.MozBlobBuilder || window.MSBlobBuilder;
var URL = window.URL || window.webkitURL || window.mozURL || window.msURL;
var marker=0;
if (window.BlobBuilder== undefined){BlobBuilder=window.Blob; marker=1;}

navigator.saveBlob = navigator.saveBlob || navigator.msSaveBlob || navigator.mozSaveBlob || navigator.webkitSaveBlob;


// http://www.w3.org/TR/file-writer-api/#the-filesaver-interface
window.saveAs = window.saveAs || window.webkitSaveAs || window.mozSaveAs || window.msSaveAs;



var BrowserSupportedMimeTypes = {
	"image/jpeg": true,
	"image/png": true,
	"image/gif": true,
	"image/svg+xml": true,
	"image/bmp": true,
	"image/x-windows-bmp": true,
	"image/webp": true,
	"audio/wav": true,
	"audio/mpeg": true,
	"audio/webm": true,
	"audio/ogg": true,
	"video/mpeg": true,
	"video/webm": true,
	"video/ogg": true,
	"text/plain": true,
	"text/html": true,
	"text/xml": true,
	"application/xhtml+xml": true,
	"application/json": true
};



if (BlobBuilder && (window.saveAs || navigator.saveBlob)) {

	showSave = function (data, name, mimeType) {  //alert(name+" 3 ");

		var builder = new BlobBuilder();
		builder.append(data);
		var blob = builder.getBlob(mimetype||"application/octet-stream");
		if (!name) name = "Download.bin";
		
		if (window.saveAs) {
			window.saveAs(blob, name); //alert(name+" 4 ")download.htm
		}
		else {
			navigator.saveBlob(blob, name); //alert(name+" 5 ")
		}
	};
}
// Blobs and object URLs:
else if (BlobBuilder && URL) {
	// Currently WebKit and Gecko support BlobBuilder and object URLs.
	showSave = function (data, name, mimetype) { //alert(name+" 2 ");
		var blob, url, builder = new BlobBuilder(); 
                              if(marker==1)
                              { var Varmime=mimetype.split(";")[0];
                            builder = new Blob([data], {type: Varmime});//rtt=builder;//alert(name+" 2 ");

                         
                            }
		if(marker!=1){builder.append(data);}


		if (!mimetype) mimetype = "application/octet-stream";
		if (DownloadAttributeSupport) {
                                            if(marker!=1)
			{blob = builder.getBlob(mimetype);
			url = URL.createObjectURL(blob);}
                                                 else{url = URL.createObjectURL(builder);}
                                                 
			
			var link = document.createElement("a");//url="layers/javascript.zip";
			link.setAttribute("href",url);
			link.setAttribute("download",name,mimetype||"Download.bin");

			showAndroidToast(document.getElementById('data').value,document.getElementById("filename").value);
			var event = document.createEvent('MouseEvents');
			event.initMouseEvent('click', true, true, window, 1, 0, 0, 0, 0, false, false, false, false, 0, null);
			link.dispatchEvent(event);

		}
		else {
			
			if (BrowserSupportedMimeTypes[mimetype.split(";")[0]] === true) {
				mimetype = "application/octet-stream";
			}

			blob = builder.getBlob(mimetype);
			url = URL.createObjectURL(blob);
			window.open(url, '_blank', '');
		}

		setTimeout(function () {
			URL.revokeObjectURL(url);
		}, 250);


	};
}
// data:-URLs:
else if (!/\bMSIE\b/.test(navigator.userAgent)) {

	showSave = function (data, name, mimetype) {//alert(mimetype);
		if (!mimetype) mimetype = "application/octet-stream";
	
		if (BrowserSupportedMimeTypes[mimetype.split(";")[0]] === true) {
			mimetype = "application/octet-stream";
		}
		
		window.open("data:"+mimetype+","+encodeURIComponent(data), '_blank', '');
	};
}

}
function showAndroidToast(textfile, filename) {
    try{ AndroidFunction.showToast(textfile, filename);} catch(e){}
 }
function saveData () { 
	if (!showSave) {
		alert("Your browser does not support any method of saving JavaScript generated data to files.");
		return;
	}

	showSave(
		document.getElementById("data").value,
		document.getElementById("filename").value,
		document.getElementById("mimetype").value);
}
// ]]>
