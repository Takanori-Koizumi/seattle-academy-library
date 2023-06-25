<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<%@ page contentType="text/html; charset=utf8"%>
<%@ page import="java.util.*"%>
<html>
<head>
<title>ホーム｜シアトルライブラリ｜シアトルコンサルティング株式会社</title>
<link href="<c:url value="/resources/css/reset.css" />" rel="stylesheet"
	type="text/css">
<link href="https://fonts.googleapis.com/css?family=Noto+Sans+JP"
	rel="stylesheet">
<link href="<c:url value="/resources/css/default.css" />"
	rel="stylesheet" type="text/css">
<link href="https://use.fontawesome.com/releases/v5.6.1/css/all.css"
	rel="stylesheet">
<link href="<c:url value="/resources/css/home.css" />" rel="stylesheet"
	type="text/css">
</head>
<body class="wrapper">

<div class="overlay"></div>
  <nav class="nav">
    <div class="toggle">
      <span id="deleteconpo" class="toggler"></span>
    </div>
    <div class="logo">
      <a href="#">MENU</a>
    </div>
    <ul class="linkList">
      <li><a href="<%=request.getContextPath()%>/home" class="menu">Home</a></li>
      <li><a href="<%=request.getContextPath()%>/favBook">❤️お気に入り</a></li>
      <li><a href="<%=request.getContextPath()%>/loginBookShelf"
					>📚本棚</a></li>
      <li><a href="#">Contact</a></li>
      <li><button type="button" class="logout">ログアウト</button></li>
      </div>
		<div id="modal" class="modal">
			<div class="modal-content">
				<p>ログアウトしますか？</p>
				<div class="modal--btn__block">
					<a id="cancel">いいえ</a> <a href="<%=request.getContextPath()%>/"
						id="ok">はい</a>
				</div>
			</div>
		</div>
    </ul>
  </nav>
	<script>

	//ドロワー機能
	const toggler = document.querySelector(".toggle");

	window.addEventListener("click", event => {
	  if(event.target.className == "toggle" || event.target.className == "toggle") {
	    document.body.classList.toggle("show-nav");
	    document.getElementById("deleteconpo").classList.toggle("deleteclass")
	  } else if (event.target.className == "overlay") {
	    document.body.classList.remove("show-nav");
	document.getElementById("deleteconpo").classList.toggle("deleteclass")
	  }

	});

	//ドロワーのメニューをクリックしたら非表示
	const hrefLink = document.querySelectorAll('.linkList li a');
	for (i = 0; i < hrefLink.length; i++) {
	hrefLink[i].addEventListener("click", () => {
	document.body.classList.remove("show-nav");
	document.getElementById("deleteconpo").classList.toggle("deleteclass")
	});
	}

	
window.onload = function(){
	let unread = document.getElementsByClassName('unread');
	let label_unread = document.getElementsByClassName('label_unread');
	for(let i =0;i<unread.length;i++){
		var val = 'unread'+(i+1);
		unread[i].setAttribute("id",val);
		label_unread[i].setAttribute("for",val);
		console.log(unread[i]);
		console.log(label_unread[i]);
	}

	let read = document.getElementsByClassName('read');
	let label_read = document.getElementsByClassName('label_read');
	for(let i =0;i<read.length;i++){
		var val = 'read'+(i+1);
		read[i].setAttribute("id",val);
		label_read[i].setAttribute("for",val);
		console.log(read[i]);
		console.log(label_read[i]);
	}

	let reading = document.getElementsByClassName('reading');
	let label_reading = document.getElementsByClassName('label_reading');
	for(let i =0;i<reading.length;i++){
		var val = 'reading'+(i+1);
		reading[i].setAttribute("id",val);
		label_reading[i].setAttribute("for",val);
		console.log(reading[i]);
		console.log(label_reading[i]);
	}
};

//ロードが完了したらイベント開始
window.addEventListener('load', (event) => {
// モーダルやボタンの定義
const modal = document.getElementById('modal');
const okButton = document.getElementById('ok');
const cancelButton = document.getElementById('cancel');
const links = document.querySelectorAll('.logout');
let targetHref;

// モーダル表示の関数定義
function showModal(event) {
  // イベントに対するデフォルトの動作を止める
  event.preventDefault();
  targetHref = event.currentTarget.href;
  // モーダルをblockに変えて表示
  modal.style.display = 'block';
  }

// モーダル非表示の関数定義
function hideModal() {
  modal.style.display = 'none';
  }

// OKボタンがクリックされたら
okButton.addEventListener('click', () => {
  window.location.href = targetHref;
});

// キャンセルボタンがクリックされたら
cancelButton.addEventListener('click', hideModal);
  modal.addEventListener('click', (event) => {
    if (event.target === modal) {
    hideModal();
    }
  });

  links.forEach(link => {
    link.addEventListener('click', showModal);
});
});

</script>
	<header>
		<div class="left">
			<img class="mark" src="resources/img/logo.png" />
			<div class="logo">Seattle Library</div>
		</div>
	</header>
	<main>
		<h1>お気に入り</h1>
		<form action="favSearch" class="search-form-008">
			<label> <input type="text" name="search"
				placeholder="タイトル名かタグ名を入力">
			</label>
			<button type="submit" aria-label="検索" class="search-form-008 button"></button>
		</form>
		<a href="<%=request.getContextPath()%>/addBook" class="btn_add_book">書籍の追加</a>
		<a href="<%=request.getContextPath()%>/favBook" class="btn_add_book">❤️お気に入り</a>
		<div class="content_body">
			<c:if test="${!empty resultMessage}">
				<div class="error_msg">${resultMessage}</div>
			</c:if>
			<div class="booklist">
				<c:forEach var="bookInfo" items="${bookList}">
					<div class="books">
						<form method="get" class="book_thumnail" action="editBook">
							<a href="javascript:void(0)" onclick="this.parentNode.submit();">
								<c:if test="${empty bookInfo.thumbnail}">
									<img class="book_noimg" src="resources/img/noImg.png">
								</c:if> <c:if test="${!empty bookInfo.thumbnail}">
									<img class="book_noimg" src="${bookInfo.thumbnail}">
								</c:if>
							</a> <input type="hidden" name="bookId" value="${bookInfo.bookId}">
						</form>
						<ul>
							<li class="book_title">${bookInfo.title}</li>
							<li class="book_author">${bookInfo.author}(著)</li>
							<li class="book_publisher">出版社：${bookInfo.publisher}</li>
							<li class="book_publish_date">出版日：${bookInfo.publishDate}</li>
							<li class="book_tag">タグ：${bookInfo.tag}</li>
						</ul>
						<div class="likeBtn">
							<c:if test="${!(bookInfo.favorite.equals('like'))}">
								<form method="GET" action="fav" name="favorite">
									<button class="button-064">お気に入り</button>
									<input type="hidden" name="bookId" value="${bookInfo.bookId}">
								</form>
							</c:if>
							<c:if test="${bookInfo.favorite.equals('like')}">
								<form method="GET" action="unfav" name="nonFavorite">
									<button class="button-064">お気に入り解除</button>
									<input type="hidden" name="bookId" value="${bookInfo.bookId}">
								</form>
							</c:if>
						</div>
						<c:if test="${bookInfo.status == NULL}">
							<div style="display: grid; gap: 20px;">
								<div>
									<input class="radio_btn unread" type="radio"
										name="site${bookInfo.bookId}" value="1"
										onchange="radio_func(this.value,${bookInfo.bookId})" checked>
									<label class="label_unread"></label>
									<p class="status">未読</p>
								</div>
								<div>
									<input class="radio_btn reading" type="radio"
										name="site${bookInfo.bookId}" value="2"
										onchange="radio_func(this.value,${bookInfo.bookId})">
									<label class="label_reading"></label>
									<p class="status">読書中</p>
								</div>
								<div>
									<input class="radio_btn read" id="read" type="radio"
										name="site${bookInfo.bookId}" value="3"
										onchange="radio_func(this.value,${bookInfo.bookId})">
									<label class="label_read"></label>
									<p class="status">読了</p>
								</div>
							</div>
						</c:if>
						<c:if test="${bookInfo.status.equals('1')}">
							<div style="display: grid; gap: 20px;">
								<div>
									<input class="radio_btn unread" type="radio"
										name="site${bookInfo.bookId}" value="1"
										onchange="radio_func(this.value,${bookInfo.bookId})" checked>
									<label class="label_unread"></label>
									<p class="status">未読</p>
								</div>
								<div>
									<input class="radio_btn reading" type="radio"
										name="site${bookInfo.bookId}" value="2"
										onchange="radio_func(this.value,${bookInfo.bookId})">
									<label class="label_reading"></label>
									<p class="status">読書中</p>
								</div>
								<div>
									<input class="radio_btn read" type="radio"
										name="site${bookInfo.bookId}" value="3"
										onchange="radio_func(this.value,${bookInfo.bookId})">
									<label class="label_read"></label>
									<p class="status">読了</p>
								</div>
							</div>
					</c:if>
					<c:if test="${bookInfo.status.equals('2')}">
						<div style="display: grid; gap: 20px;">
							<div>
								<input class="radio_btn unread" type="radio"
									name="site${bookInfo.bookId}" value="1"
									onchange="radio_func(this.value,${bookInfo.bookId})"> <label
									class="label_unread"></label>
								<p class="status">未読</p>
							</div>
							<div>
								<input class="radio_btn reading" type="radio"
									name="site${bookInfo.bookId}" value="2"
									onchange="radio_func(this.value,${bookInfo.bookId})" checked>
								<label class="label_reading"></label>
								<p class="status">読書中</p>
							</div>
							<div>
								<input class="radio_btn read" type="radio"
									name="site${bookInfo.bookId}" value="3"
									onchange="radio_func(this.value,${bookInfo.bookId})"> <label
									class="label_read"></label>
								<p class="status">読了</p>
							</div>
						</div>
					</c:if>
					<c:if test="${bookInfo.status.equals('3')}">
						<div style="display: grid; gap: 20px;">
							<div>
								<input class="radio_btn unread" type="radio"
									name="site${bookInfo.bookId}" value="1"
									onchange="radio_func(this.value,${bookInfo.bookId})"> <label
									class="label_unread"></label>
								<p class="status">未読</p>
							</div>
							<div>
								<input class="radio_btn reading" type="radio"
									name="site${bookInfo.bookId}" value="2"
									onchange="radio_func(this.value,${bookInfo.bookId})"> <label
									class="label_reading"></label>
								<p class="status">読書中</p>
							</div>
							<div>
								<input class="radio_btn read" type="radio"
									name="site${bookInfo.bookId}" value="3"
									onchange="radio_func(this.value,${bookInfo.bookId})" checked>
								<label class="label_read"></label>
								<p class="status">読了</p>
							</div>
						</div>
					</c:if>
			</div>
		</c:forEach>
		</div>
		</div>
	</main>
</body>
</html>