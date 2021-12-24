// (1) 회원정보 수정
function update(userId, event) {

    event.preventDefault(); // 폼 태그 액션을 막기

    // form id="profileUpdate" 의 정보 불러오기 제이쿼리(header.jsp에 설정되어있음)
    let data = $("#profileUpdate").serialize();
    console.log(data);

    $.ajax({
        type : "put",
        url : `/api/user/${userId}`,
        data : data,
        contentType : "application/x-www-form-urlencoded; charset=utf-8",
        dataType : "json"
    }).done(res => {  // HttpStatus 상태코드 200번대 일때
        console.log("성공", res)
        location.href = `/user/${userId}`
    }).fail(error => {    // HttpStatus 상태코드 200번대가 아닐때
        alert(JSON.stringify(error.responseJSON.data))
        console.log("실패", error.responseJSON.data)
    });

}