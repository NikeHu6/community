//提交回复
function post() {
    var questionId = $("#question_id").val();
    console.log(content);
    var content = $("#comment_content").val();
    comment2target(questionId,1,content);
}

function comment2target(targetId,type,content) {

    if (!content) {
        alert("评论内容不能为空");
        return;
    }
    console.log(content);
    $.ajax({
        type: "POST",
        url: "/comment",
        contentType:'application/json',
        data: JSON.stringify(
            {
                "parentId":targetId,
                "content":content,
                "type":type
            }
        ),
        success: function (response) {
            if (response.code == 200){
                window.location.reload();
                $("#comment_section").hide();
            }else{
                if (response.code == 2003){
                    var isAccepted = confirm(response.message);
                    if (isAccepted){
                        window.open(href="https://github.com/login/oauth/authorize?client_id=3d747560b7e40dc2a2e8&redirect_uri=http://localhost:8081/callback&scope=user&state=1")
                        window.localStorage.setItem("closable",true);

                    }
                }else{
                    alert(response.message);
                }

            }
            console.log(response);
        },
        dataType: "json"
    });
}

//获取评论
function comment(e) {
    var commentId = e.getAttribute("data-id");
    var content = $("#input-"+commentId).val();
    comment2target(commentId,2,content)
}

//展开二级评论
function collapseComments(e) {
    var id = e.getAttribute("data-id");
    var comments = $("#comment-"+id);

    //获取二级评论展开状态
    var collapse = e.getAttribute("data-collapse");
    if (collapse){
        //折叠二级评论
        comments.removeClass("in");
        e.removeAttribute("data-collapse");
        e.classList.remove("active");
    } else{
        var subCommentContainer = $("#comment-"+id);
        if (subCommentContainer.children().length != 1){
            //展开二级评论
            comments.addClass("in");
            //标记二级评论状态
            e.setAttribute("data-collapse","in");
            e.classList.add("active");
        } else {
            $.getJSON( "/comment/"+id, function( data ) {
                console.log(data)


                var items = [];
                $.each( data.data.reverse(), function(index,comment ) {

                    var mediaLeft = $("<div/>",{
                       "class":"media-left media-middle",
                    }).append($("<img/>",{
                        "class":"media-object img-rounded",
                        "src":comment.user.avatarUrl,
                    }));


                    var mediaBody = $("<div/>",{
                        "class":"media-body",
                    }).append($("<h5/>",{
                        "class":"media-heading media-body",
                        "html":comment.user.name,
                    })).append($("<div/>",{
                        "html":comment.content,
                    })).append($("<div/>",{
                        "class":"menu",
                    }).append($("<span/>",{
                        "class":"pull-right",
                        "html":moment(comment.gmtCreate).format("YYYY-MM-DD")
                    })));


                    var media = $("<div/>",{
                        "class":"media comment-content",
                    }).append(mediaLeft).append(mediaBody);

                    subCommentContainer.prepend(media);
                });


            });
            //展开二级评论
            comments.addClass("in");
            //标记二级评论状态
            e.setAttribute("data-collapse","in");
            e.classList.add("active");
        }

    }
}

function selectType(e) {
    var value = e.getAttribute("data-tag");
    var previous = $("#tag").val();
    var tags = previous.split(",");
    if (tags.indexOf(value) == -1){
        if (previous){
            $("#tag").val(previous+","+value);
        } else {
            $("#tag").val(value);
        }
    }
}


function showSelectTag() {
    $("#select-tag").show();
}

function hideSelectTag() {
    $("#select-tag").hide();
}