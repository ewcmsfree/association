tinymce.init({
	// General options
	directionality : 'ltr',//设置编辑器的默认方向：ltr从左到右，rtl从右到左
	selector : "textarea.mceEditor",
	theme : "modern",
	language : 'zh_CN',
	skin : "xenmce",
	convert_urls : false,//不作任何url转换
	//remove_script_host : false,
	//relative_urls : false, 
	plugins : "pagebreak,layer,table,preview,media,searchreplace,print,contextmenu,paste,directionality,fullscreen,noneditable,visualchars,nonbreaking,template",
	// Theme options
	toolbar1 : "cut,copy,paste,pastetext,pasteword,|,search,replace,|,bullist,numlist,|,outdent,indent,blockquote,|,undo,redo,|,link,unlink,anchor,cleanup,|,preview,|,forecolor,backcolor,|,tablecontrols,|,hr,removeformat,visualaid,|,sub,sup,|,charmap,emotions,iespell,advhr,|,print,|,ltr,rtl,|,ewcmsImage,ewcmsAnnex,ewcmsVote,ewcmsFlash,ewcmsVideo",
	toolbar2 : "formatselect,fontselect,fontsizeselect,|,bold,italic,underline,strikethrough,|,justifyleft,justifycenter,justifyright,justifyfull,|,insertlayer,moveforward,movebackward,absolute,|,styleprops,|,cite,abbr,acronym,del,ins,attribs,|,visualchars,nonbreaking,template,pagebreak,|,fullscreen,code,|,help",
	//toolbar3 : "",
	//toolbar4 : "",
	menubar: false,
    toolbar_items_size: 'small',
    resize: false,

	theme_advanced_toolbar_location : "top",
	theme_advanced_toolbar_align : "left",
	theme_advanced_statusbar_location : "bottom",
	theme_advanced_resizing : false,
	content_css : tinymce.baseURI.getURI() + "/css/content.css",
	//execcommand_callback : "ArticleEdit.windowResize",
	theme_advanced_fonts : "宋体=宋体;"+
	  					   "仿宋_GB2312=仿宋_GB2312;"+
	   					   "方正舒体=方正舒体;"+
	   					   "黑体=黑体;"+
	   					   "华文彩云=华文彩云;"+
	   					   "华文仿宋=华文仿宋;"+
	   					   "华文琥珀=华文琥珀;"+
	   					   "华文楷体=华文楷体;"+
	   					   "华文隶书=华文隶书;"+
	   					   "华文宋体=华文宋体;"+
	   					   "华文细黑=华文细黑;"+
	   					   "华文新魏=华文新魏;"+
	   					   "华文行楷=华文行楷;"+
	   					   "华文中宋=华文中宋;"+
	   					   "楷体_GB2312=楷体_GB2312;"+
	   					   "隶书=隶书;"+
	   					   "新宋体=新宋体;"+
	   					   "幼圆=幼圆;"+
	   					   "Andale Mono=andale mono,times;"+
	   					   "Arial=arial,helvetica,sans-serif;"+
	   					   "Arial Black=arial black,avant garde;"+
	   					   "Book Antiqua=book antiqua,palatino;"+
	   					   "Comic Sans MS=comic sans ms,sans-serif;"+
	   					   "Courier New=courier new,courier;"+
	   					   "Georgia=georgia,palatino;"+
	   					   "Helvetica=helvetica;"+
	   					   "Impact=impact,chicago;"+
	   					   "Symbol=symbol;"+
	   					   "Tahoma=tahoma,arial,helvetica,sans-serif;"+
	   					   "Terminal=terminal,monaco;"+
	   					   "Times New Roman=times new roman,times;"+
	   					   "Trebuchet MS=trebuchet ms,geneva;"+
	   					   "Verdana=verdana,geneva;"+
	   					   "Webdings=webdings;"+
	   					   "Wingdings=wingdings,zapf dingbats",
	setup : function(editor) {
		editor.addButton('ewcmsImage',{
		   	text : '插入图片',
		   	image : tinymce.baseURI.getURI() + '/image/image.gif',
		   	onclick : function() {
		   		editor.focus();
		   		ArticleEdit.openImageWindow(true);
	       	}
		});
		editor.addButton('ewcmsAnnex',{
			text : '插入附件',
			icon : '/image/annex.gif',
			onclick : function(){
				editor.focus();
				ArticleEdit.openAnnexWindow();
			}
		});
		editor.addButton('ewcmsVote',{
			text : '插入调查投票',
			icon : editor.baseURI.getURI() + '/image/vote.gif',
			onclick : function(){
				editor.focus();
				ArticleEdit.openVoteWindow();
			}
		});
		editor.addButton('ewcmsFlash',{
			text : '插入Flash',
			icon : editor.baseURI.getURI() + '/image/flash.gif',
			onclick : function(){
				editor.focus();
				ArticleEdit.openFlashWindow();
			}
		});
		editor.addButton('ewcmsVideo',{
			text : '插入视频',
			icon : editor.baseURI.getURI() + '/image/video.gif',
			onclick : function(){
				editor.focus();
				ArticleEdit.openVideoWindow();
			}
		});
	}
    // Drop lists for link/image/media/template dialogs
    //template_external_list_url : "js/template_list.js",
    //external_link_list_url : "js/link_list.js",
    //external_image_list_url : "js/image_list.js",
    //media_external_list_url : "js/media_list.js",
    // Replace values for the template plugin
    //template_replace_values : {
    //        username : "Some User",
    //        staffid : "991234"
    //}
});
