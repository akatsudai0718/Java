$(function() {

$("#add").on("click", function() {
	const form = {
			subject : $('#input-text-subject').val(),
			deadLine : $("#input-date-deadline").val(),
			hasDone : $("#gridCheck").prop("checked")
			}
	console.log("通過「add」");
	console.log(form.hasDone);

	$.ajax({
		url:"http://localhost:8081/todo/add",
        type:"GET",
		data:form
	}).done(function(data) {
		console.log("通信成功");
		console.log(data);

		$('table tbody').children().remove();

		$.each(data, function(index, val) {
			addTask = '\n\
				<tr class="' + ((val["done"]) ? "table-dark" : "") + '" id=' + (val["id"]) + '>\n\
					<input type="hidden" id="hasDoneContent-' + (val["id"]) + '" value=' + (val["done"]) + '>\n\
					<td id="subjectContent-' + (val["id"]) + '">' + (val["subject"]) + '</td>\n\
					<td id="deadLineContent-' + (val["id"]) + '">' + (val["dead-line"]) + '</td>\n\
					<td>\n\
					<button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#exampleModal" data-bs-whatever="test" id="editPopup">編集</button>\n\
					</td>\n\
					<td>\n\
						<form>\n\
							<input type="button" class="' + ((val["done"]) ? "btn btn-light" : "btn btn-danger") + '" id="delete" value="削除" />\n\
						</form>\n\
					</td>\n\
				</tr>\n\
			';

			$("table tbody").append(addTask);
		});
	}).fail(function(data) {
        alert("通信失敗");
    });
});

$(document).on("click", "#delete", function() {
	var deleteId = $(this).parent().parent().parent().attr('id');
	console.log("通過「delete」");
	console.log(deleteId);

	$.ajax({
		url:"http://localhost:8081/todo/delete",
        type:"GET",
		data:{id:deleteId}
	}).done(function(data) {
		console.log("通信成功");
		$("#" + deleteId).remove();
	}).fail(function(data) {
        alert("通信失敗");
    });
});

// $(document).on("click", "#edit", function() {
// 	var editId = $(this).parent().parent().parent().attr('id');
// 	console.log("通過「edit」");
// 	console.log(editId);

// 	$.ajax({
// 		url:"http://localhost:8081/todo/tasks",
//         type:"GET"
// 	}).done(function(data) {
// 		console.log("通信成功");

// 		editForm = '\n\
// 			<div class="col-md-8" id="editor">\n\
// 				<input type="hidden" id="editId" value="' + editId + '">\n\
// 				<h1>登録フォーム</h1>\n\
// 				<tr class="todo">\n\
// 					<form method="POST" action="./tasks.html" th:action="@{/tasks}">\n\
// 						<input name="_method" type="hidden" value="PUT">\n\
// 						<div class="form-row">\n\
// 							<div class="form-group col-md-8">\n\
// 								<label for="input-text-subject">タスク</label>\n\
// 								<input class="form-control" required="required" type="text" placeholder="やること" th:field="${form.subject}" id="input-text-subject" />\n\
// 							</div>\n\
// 							<div class="form-group col-md-4">\n\
// 								<label for="input-date-deadline">期限</label>\n\
// 								<input class="form-control" required="required" type="date" th:field="${form.deadLine}" id="input-date-deadline" />\n\
// 							</div>\n\
// 						</div>\n\
// 						<div class="form-group">\n\
// 							<div class="form-check">\n\
// 								<input class="form-check-input" type="checkbox" th:field="${form.hasDone}" id="gridCheck">\n\
// 								<label class="form-check-label" for="gridCheck">完了チェック</label>\n\
// 							</div>\n\
// 						</div>\n\
// 						<button type="button" class="btn btn-success" id="update">更新</button>\n\
// 						<a type="button" class="btn btn-warning" href="./tasks.html" th:href="@{/tasks}">クリア</a>\n\
// 					</form>\n\
// 				</tr>\n\
// 			</div>\n\
// 		';

// 		$('#register').replaceWith(editForm);
// 	}).fail(function(data) {
//         alert("通信失敗");
//     });
// });

$(document).on("click", "#editPopup", function() {
	var isHasDone = false;

	var editId = $(this).parent().parent().attr('id');
	var updateTextSubject = $('#subjectContent-' + editId).text();
	var updateTextDeadLine = $('#deadLineContent-' + editId).text();
	var updateTextHasDone = $('#hasDoneContent-' + editId).val();

	if(updateTextHasDone === "true") {
		isHasDone = true;
	}

	console.log(editId);
	console.log(updateTextSubject);
	console.log(updateTextDeadLine);
	console.log(updateTextHasDone);

	$.ajax({
		url:"http://localhost:8081/todo/tasks",
        type:"GET"
	}).done(function(data) {
		console.log("通信成功");

		$("#updatePoupId").val(editId);
		$("#update-text-subject").val(updateTextSubject);
		$("#update-date-deadline").val(updateTextDeadLine);
		$("#update-gridCheck").prop("checked", isHasDone);

	}).fail(function(data) {
        alert("通信失敗");
    });
});

$(document).on("click", "#update", function() {
	// const id = $(this).parent().parent().attr('id');
	const id = $('#updatePoupId').val();

	const subject = $('#update-text-subject').val();
	const deadLine = $("#update-date-deadline").val();
	const hasDone = $("#update-gridCheck").prop("checked");

	console.log("通過「update」");
	console.log(id);

	$.ajax({
		url:"http://localhost:8081/todo/put",
        type:"GET",
		data:{id : id,
			subject : subject,
			deadLine : deadLine,
			hasDone : hasDone
		}
	}).done(function(data) {
		console.log("通信成功");
		console.log(data);

		$('table tbody').children().remove();

		$.each(data, function(index, val) {
			updateTask = '\n\
			<tr class="' + ((val["done"]) ? "table-dark" : "") + '" id=' + (val["id"]) + '>\n\
			<input type="hidden" id="hasDoneContent-' + (val["id"]) + '" value=' + (val["done"]) + '>\n\
			<td id="subjectContent-' + (val["id"]) + '">' + (val["subject"]) + '</td>\n\
			<td id="deadLineContent-' + (val["id"]) + '">' + (val["dead-line"]) + '</td>\n\
			<td>\n\
			<button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#exampleModal" data-bs-whatever="test" id="editPopup">編集</button>\n\
			</td>\n\
			<td>\n\
				<form>\n\
					<input type="button" class="' + ((val["done"]) ? "btn btn-light" : "btn btn-danger") + '" id="delete" value="削除" />\n\
				</form>\n\
			</td>\n\
		</tr>\n\
			';

			$("table tbody").append(updateTask);
		});

	}).fail(function(data) {
        alert("通信失敗");
    });
});

var exampleModal = document.getElementById('exampleModal')
exampleModal.addEventListener('show.bs.modal', function (event) {
// Button that triggered the modal
var button = event.relatedTarget
// Extract info from data-bs-* attributes
var recipient = button.getAttribute('data-bs-whatever')
// If necessary, you could initiate an AJAX request here
// and then do the updating in a callback.
//
// Update the modal's content.
var modalTitle = exampleModal.querySelector('.modal-title')
var modalBodyInput = exampleModal.querySelector('.modal-body input')

modalTitle.textContent = 'タスクの編集'
modalBodyInput.value = recipient
});

});