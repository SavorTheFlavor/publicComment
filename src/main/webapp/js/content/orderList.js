$(function() {
	common.showMessage($("#message").val());
});

function search(currentPage) {
	$("#currentPage").val(currentPage);
	$("#mainForm").submit();
}

function remove2(id) {
	if(confirm("确定要删除这条订单吗？")) {
		$("#id").val(id);
		$("#mainForm").attr("action",$("#basePath").val() + "/orders/remove");
		$("#mainForm").submit();
	}
}
