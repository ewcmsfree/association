document
		.write('<div id="loading_mask" style="position: absolute; left: 0; top: 0; background: #ccc; opacity: 0.3; filter: alpha(opacity = 30); display: block; width: 100%; height: 100%"></div>');
document
		.write('<div id="loading_mask_msg" style="position: absolute; cursor1: wait; width: 200px; height: 16px;top:'
				+ ((document.documentElement.clientHeight - 110) / 2)
				+ 'px;left:'
				+ ((document.documentElement.clientWidth - 200) / 2)
				+ 'px;  padding: 12px 5px 10px 30px; background: #fff url() no-repeat scroll 5px 10px; border: 2px solid #6593CF; color: #222; display: block;font-size:12px;">页面正在加载，请稍候。。。</div>');

if (window.addEventListener) {// for Mozilla/Opera9
	document.onreadystatechange = function() {
		if (document.readyState == 'complete') {
			document.getElementById('loading_mask').style.display = 'none';
			document.getElementById('loading_mask_msg').style.display = 'none';
		}
	};
} else if (window.attachEvent) {// for IE
	window.attachEvent('onload', function() {
		document.getElementById('loading_mask').style.display = 'none';
		document.getElementById('loading_mask_msg').style.display = 'none';
	}, false);
}
//document.getElementById('loading_mask').parentNode.removeChild(document.getElementById('loading_mask'));
//document.getElementById('loading_mask_msg').parentNode.removeChild(document.getElementById('loading_mask_msg'));