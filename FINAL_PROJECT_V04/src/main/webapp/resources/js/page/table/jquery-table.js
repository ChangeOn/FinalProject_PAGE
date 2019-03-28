"use strict";

function IterarCamposEdit(t,n){
	function i(t){
		if(null==colsEdi)
			return !0;
		for(var n=0;n<colsEdi.length;n++)
			if(t==colsEdi[n])
				return !0;
		return!1
		}
	var o=0;
	t.each(
			function(){
				o++,"buttons"!=$(this).attr("name")&&i(o-1)&&n($(this))
				}
			)
}

function FijModoNormal(t){
	$(t).parent().find("#bAcep").hide()
	,$(t).parent().find("#bCanc").hide()
	,$(t).parent().find("#bEdit").show()
	,$(t).parent().find("#bElim").show()
	,$(t).parents("tr").attr("id","")
}

function FijModoEdit(t){
	$(t).parent().find("#bAcep").show()
	,$(t).parent().find("#bCanc").show()
	,$(t).parent().find("#bEdit").hide()
	,$(t).parent().find("#bElim").hide()
	,$(t).parents("tr").attr("id","editing")
}

function ModoEdicion(t){
	return"editing"==t.attr("id")
}

function rowAcep(t){
	var n=$(t).parents("tr")
	,i=n.find("td");
	ModoEdicion(n)&&(IterarCamposEdit(i, function(t){
		var n=t.find("input").val();
		t.html(n)
		})
		,FijModoNormal(t),params.onEdit(n)
		)
}

function rowCancel(t){
	var n=$(t).parents("tr")
	,i=n.find("td");
	ModoEdicion(n)&&(IterarCamposEdit(i,function(t){
		var n=t.find("div").html();
		t.html(n)
		})
		,FijModoNormal(t)
		)
}

function rowEdit(t){
	var n=$(t).parents("tr")
	,i=n.find("td");
	ModoEdicion(n)||(IterarCamposEdit(i,function(t){
		var n=t.html()
		,i='<div style="display: none;">'+n+"</div>",o='<input class="form-control input-sm"  value="'+n+'">';
		t.html(i+o)
		})
		,FijModoEdit(t)
		)
}

// 테이블 에디터 제거
function Table_Warnning(t) {
	
	// 상단 네비게이션 바 초기화
	before_alert();
	
	$('#table-destroy-warnning').collapse('show');
}

// 테이블 한 행 제거
function rowElim(t){
	
	// 최소 1개의 행은 유지하도록 설정
	if($('#editor-table').find("tbody tr").length>1) {
		$(t).parents("tr").remove()
		,params.onDelete()
	}
	// 1개의 행만 남았을 경우
	else {
		
		// 상단 네비게이션 바 초기화
		before_alert();
		
		$('#table-row-warnning').collapse('show');
	}
}

function rowAgreg(){
	if(0==$tab_en_edic.find("tbody tr").length){
		var t="";
		(i=$tab_en_edic.find("thead tr").find("th")).each(function(){
			"buttons"==$(this).attr("name")?t+=colEdicHtml:t+="<td></td>"
				})
				,$tab_en_edic.find("tbody").append("<tr>"+t+"</tr>")
	}
	else{
		var n=$tab_en_edic.find("tr:last");
		n.clone().appendTo(n.parent());
		var i=(n=$tab_en_edic.find("tr:last")).find("td");
		i.each(function(){
			"buttons"==$(this).attr("name")||$(this).html("")}
		)}
}

function TableToCSV(t){
	var n="",i="";
	return $tab_en_edic.find("tbody tr").each(function(){
		ModoEdicion($(this))&&$(this).find("#bAcep").click();
		var o=$(this).find("td");
		n="",o.each(function(){
			"buttons"==$(this).attr("name")||(n=n+$(this).html()+t)
			})
			,""!=n&&(n=n.substr(0,n.length-t.length)),i=i+n+"\n"
			})
			,i
		}

var $tab_en_edic=null
,params=null
,colsEdi=null
,newColHtml=
	'<div class="btn-group pull-right">'
	+'<button id="bEdit" type="button" class="btn btn-sm btn-default" onclick="rowEdit(this);">'
	+'<i class="far fa-edit"></i></button>'
	+'<button id="bElim" type="button" class="btn btn-sm btn-default" onclick="rowElim(this);">'
	+'<i class="far fa-trash-alt"></i></button>'
	+'<button id="bAcep" type="button" class="btn btn-sm btn-default" style="display:none;" onclick="rowAcep(this);">'
	+'<i class="fas fa-check"></i></button>'
	+'<button id="bCanc" type="button" class="btn btn-sm btn-default" style="display:none;" onclick="rowCancel(this);">'
	+'<i class="fas fa-times"></i></button>'
	+'</div>'
	,colEdicHtml='<td name="buttons">'+newColHtml+"</td>";

//부가 기능 설정
$.fn.SetEditable=function(t){
	var n={
			columnsEd:null
			// 추가 행 삽입 버튼
			,$addButton:null
			,onEdit:function(){}
	,onDelete:function(){}
	,onAdd:function(){}
	,onDestroy:function(){}
	};
	
	// 전해받은 파라미터로 기본값 수정
	params=$.extend(n,t)
	,this.find("thead tr").append('<th name="th-buttons"></th>')
	,this.find("[name^='th-']").append(
			"<button id='destroy-table' class='btn btn-sm btn-default' style='color:red'" 
			+"onclick='Table_Warnning(this);'>" 
			+"<i class='fas fa-times-circle icon-red'"
			+"style='vertical-align: bottom;'></i>"
			+"</button>"	
	)
	,this.find("tbody tr").append(colEdicHtml)
	,$tab_en_edic=this
	,null!=params.columnsEd&&(colsEdi=params.columnsEd.split(","))
	
	// 추가 행 삽입 버튼 설정 시
	if(params.$addButton!=null) {
		console.log('true');
		this.find("[name^='th-']").prepend(
				"<button id='add-new-row' class='btn btn-sm btn-default'>"
				+"<i class='fas fa-plus-square'"
				+"style='vertical-align: bottom;'></i>"
				+"</button>"	
		);
		// 버튼 클릭 이벤트 설정
		$('#add-new-row').click(function(){
			rowAgreg()
			$(this).blur();
		});
	}
	};
