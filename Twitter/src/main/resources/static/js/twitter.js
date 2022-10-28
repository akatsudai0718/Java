$(function() {

	function updateAllTweet(data) {
		$('table').children('tbody').remove();

		$.each(data, function(index, val) {

			st_retweet = "Retweet";
			button_retweet = 'btn-outline-success';
			button_good = 'btn-outline-danger';

			if(val["_retweet"]) {
				st_retweet = "Undo Retweet"
				button_retweet = 'btn-success';
			}

			if(val["_good"]) {
				button_good = 'btn-danger';
			}

			var addTask = '\n\
				<tbody>\n\
					<tr>\n\
						<td class="fw-bold">' + (val["account_name"]) + '</td>\n\
						<td id="updatetime-' + (val["id"]) + '" class="table-timestamp">' + (val["update_at"]) + '</td>\n\
					</tr>\n\
					<tr>\n\
						<td colspan="2" "content-' + (val["id"]) + '">' + (val["content"]) + '</td>\n\
					</tr>\n\
					<tr class="table-ul">\n\
						<td colspan="2">\n\
							<div>\n\
								<li>\n\
									<button id="reply-' + (val["id"]) + '" type="button" class="reply_button btn btn-outline-primary btn-sm rounded-pill text-nowrap" data-bs-toggle="modal" data-bs-target="#replyModal" data-bs-whatever="Reply"><i class="fa-regular fa-comment-dots"></i></button>\n\
									<span>' + (val["number_of_reply"]) + '</span>\n\
								</li>\n\
								<li>\n\
									<a tabindex="0" role="button" id="retweet-' + (val["id"]) + '" class="btn ' + button_retweet + ' btn-sm rounded-pill text-nowrap" data-bs-toggle="popover" data-bs-trigger="focus" data-bs-placement="bottom" data-bs-html="true" data-bs-content="<a tabindex=\'0\' role=\'button\' class=\'retweet_button btn btn-light\'><i class=\'fa-solid fa-retweet\'></i>' + st_retweet + '</a><br><a tabindex=\'0\' role=\'button\' class=\'quote_tweet_button btn btn-light\'><i class=\'fa-solid fa-pen\'></i>Quote Tweet</a>" data-bs-original-title="" title=""><i class="fa-solid fa-retweet"></i></a>\n\
									<span>' + (val["number_of_retweet"]) + '</span>\n\
								</li>\n\
								<li>\n\
									<button id="good-' + (val["id"]) + '" type="button" class="good_button btn ' + button_good + ' btn-sm rounded-pill text-nowrap"><i class="fa-regular fa-heart"></i></button>\n\
									<span>' + (val["number_of_good"]) + '</span>\n\
								</li>\n\
								<li>\n\
									<button id="share-' + (val["id"]) + '" type="button" class="delete_button btn btn-outline-info btn-sm rounded-pill text-nowrap"><i class="fa-solid fa-arrow-up-from-bracket"></i></button>\n\
									<span></span>\n\
								</li>\n\
							</div>\n\
						</td>\n\
					</tr>\n\
				</tbody>\n\
			';

			$("table").append(addTask);

		});

		var tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'))
		var tooltipList = tooltipTriggerList.map(function (tooltipTriggerEl) {
			return new bootstrap.Tooltip(tooltipTriggerEl)
		});
		var popoverTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="popover"]'));
		var popoverList = popoverTriggerList.map(function (popoverTriggerEl) {
			return new bootstrap.Popover(popoverTriggerEl);
		});
	}

	$(document).on("click", ".tweet_button", function() {

		$.ajax({
	        type:"GET"
		}).done(function(data) {
			$("#tweet-content").val("");

		}).fail(function(data) {
	        alert("通信失敗");
	    });
	});

	$(document).on("click", "#tweet", function() {
		const content = $('#tweet-content').val();

		console.log("通過「#tweet」");

		$.ajax({
			url:"http://localhost:8084/twitter/tweet",
	        type:"GET",
			data:{content : content}
		}).done(function(data) {
			console.log(data);

			updateAllTweet(data);

		}).fail(function(data) {
	        alert("通信失敗");
	    });
	});

	$(document).on("click", ".reply_button", function() {
		var tweet_id = $(this).attr('id').substr($(this).attr('id').indexOf('-') + 1);

		$.ajax({
			url:"http://localhost:8084/twitter/replyForm",
	        type:"GET",
			data:{tweet_id : tweet_id}
		}).done(function(data) {

			$("#reply-from-username").text(data["user_name"]);
			$("#reply-from-updatetime").text(data["update_at"]);
			$("#reply-from-content").text(data["content"]);
			$("#reply-tweet-id").val(tweet_id);
			$("#reply-content").val("");

		}).fail(function(data) {
	        alert("通信失敗");
	    });
	});

	$(document).on("click", "#reply", function() {
		const content = $('#reply-content').val();
		const tweet_id = $('#reply-tweet-id').val();


		$.ajax({
			url:"http://localhost:8084/twitter/reply",
	        type:"GET",
			data:{content : content,
				tweet_id: tweet_id
				}
		}).done(function(data) {
			console.log(data);

			updateAllTweet(data);

		}).fail(function(data) {
	        alert("通信失敗");
	    });
	});

	$(document).on("click", ".retweet_button", function() {
		var id = $('a[aria-describedby]').attr('id');

		var tweet_id = $('a[aria-describedby]').attr('id').substr($('a[aria-describedby]').attr('id').indexOf('-') + 1);
		console.log(tweet_id);

		$.ajax({
			url:"http://localhost:8084/twitter/retweet",
	        type:"GET",
			data:{tweet_id: tweet_id}
		}).done(function(data) {

			updateAllTweet(data);

		}).fail(function(data) {
	        alert("通信失敗");
	    });
	});

	$(document).on("click", ".quote_tweet_button", function() {
		var tweet_id = $('a[aria-describedby]').attr('id').substr($('a[aria-describedby]').attr('id').indexOf('-') + 1);

		$.ajax({
			url:"http://localhost:8084/twitter/retweetForm",
	        type:"GET",
			data:{tweet_id : tweet_id}
		}).done(function(data) {
			$("#retweet-from-username").text(data["user_name"]);
			$("#retweet-from-updatetime").text(data["update_at"]);
			$("#retweet-from-content").text(data["content"]);
			$("#retweet-tweet-id").val(tweet_id);
			$("#retweet-content").val("");

		}).fail(function(data) {
	        alert("通信失敗");
	    });
	});

	$(document).on("click", "#retweet", function() {
		const content = $('#reply-content').val();
		const reply_tweet_id = $('#reply-tweet-id').val();


		$.ajax({
			url:"http://localhost:8084/twitter/reply",
	        type:"GET",
			data:{content : content,
				reply_tweet_id: reply_tweet_id
				}
		}).done(function(data) {
			console.log(data);

			updateAllTweet(data);

		}).fail(function(data) {
	        alert("通信失敗");
	    });
	});

	$(document).on("click", ".good_button", function() {
		var id = $(this).attr('id');

		var tweet_id = $(this).attr('id').substr($(this).attr('id').indexOf('-') + 1);

		$.ajax({
			url:"http://localhost:8084/twitter/good",
	        type:"GET",
			data:{tweet_id: tweet_id}
		}).done(function(data) {
			console.log(data);

//			$("[id=" + id + "]").children().remove();
//			icon = '<i class="fa-regular fa-heart"></i>';
//
//			if(data["_good"]) {
//				icon = '<i class="fa-solid fa-heart"></i>';
//			}
//
//			$("[id=" + id + "]").append(icon);
//			$("[id=" + id + "]").next("span").text(data["number_of_good"]);

			removeClass = "btn-danger";
			addClass = "btn-outline-danger";

			if(data["_good"]) {
				removeClass = "btn-outline-danger";
				addClass = "btn-danger";
			}

			$("[id=" + id + "]").removeClass(removeClass);
			$("[id=" + id + "]").addClass(addClass);
			$("[id=" + id + "]").next("span").text(data["number_of_good"]);


		}).fail(function(data) {
	        alert("通信失敗");
	    });
	});

	$(document).on("click", ".delete_button", function() {
		var id = $(this).attr('id');

		var tweet_id = $(this).attr('id').substr($(this).attr('id').indexOf('-') + 1);

		$.ajax({
			url:"http://localhost:8084/twitter/delete",
	        type:"GET",
			data:{tweet_id: tweet_id}
		}).done(function(data) {

			updateAllTweet(data);

		}).fail(function(data) {
	        alert("通信失敗");
	    });
	});

var exampleModal = document.getElementById('tweetModal')
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

modalTitle.textContent = 'Tweet'
//modalBodyInput.value = recipient

});

var exampleModal = document.getElementById('replyModal')
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

modalTitle.textContent = 'Reply'
//modalBodyInput.value = recipient

});

var exampleModal = document.getElementById('retweetModal')
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

modalTitle.textContent = 'Retweet'
//modalBodyInput.value = recipient

});

var tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'))
var tooltipList = tooltipTriggerList.map(function (tooltipTriggerEl) {
  return new bootstrap.Tooltip(tooltipTriggerEl)
});

var popoverTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="popover"]'));
var popoverList = popoverTriggerList.map(function (popoverTriggerEl) {
  return new bootstrap.Popover(popoverTriggerEl);
});

});