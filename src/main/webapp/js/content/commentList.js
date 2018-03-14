function search(currentPage) {
	$("#mainForm").attr("method","GET");
	$("#currentPage").attr("value", currentPage);
	$("#mainForm").attr("action",$("#basePath").val() + "/businesses");
	$("#mainForm").submit();
}