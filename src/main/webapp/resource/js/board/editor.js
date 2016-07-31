
$(function(){
    $.ajax({
        url : contextPath+"/resource/daumeditor/editor_frame.jsp",
        success : function(data){
            $("#editor_frame").html(data);
            var config = {
                 txHost: '',
                txPath: '',
                txService: 'sample',
                txProject: 'sample',
                initializedId: "",
               wrapper: "tx_trex_container",
               form: "frm",
                txIconPath: contextPath+"/resource/daumeditor/images/icon/editor/",
               txDecoPath: contextPath+"/resource/daumeditor/images/deco/contents/",
                canvas: {
                    styles: {
                        /* 기본 글자색 */
                        color: "#123456",
                        /* 기본 글자체 */
                        fontFamily: "굴림",
                        /* 기본 글자크기 */
                        fontSize: "10pt",
                        /*기본 배경색 */
                        backgroundColor: "#fff",
                        /*기본 줄간격 */
                        lineHeight: "1.5",
                        /* 위지윅 영역의 여백 */
                        padding: "8px"
                    },
                    showGuideArea: false
                },
                events: {
                    preventUnload: false
                },
                sidebar: {
                    attachbox: {
                        show: true,
                        confirmForDeleteAll: true
                    }
                },
                size: {
                    /* 지정된 본문영역의 넓이가 있을 경우에 설정 */
                    contentWidth: 700
                }
            };

            //에디터내에 환경설정 적용하기
            new Editor(config);
        }
    });

    //form submit 버튼 클릭
    $("#save_button").click(function(){
        //다음에디터가 포함된 form submit
        Editor.save();
    })
})


//Editor.save() 호출 한 다음에 validation 검증을 위한 함수
//validation 체크해줄 입력폼들을 이 함수에 추가 지정해줍니다.
function validForm(editor) {
    var validator = new Trex.Validator();
    var content = editor.getContent();
    var seperate = $("#hid").val();
    
    
    if (!validator.exists(content)) {
    	alert(content);
        alert('내용을 입력하세요');
        return false;
    }
    
    if(seperate === "product"){
    	var thumbnail = $("#thumnail").val();
    	if(thumbnail === ""){
        	alert('Input thumbnail');
        	return false;
        }
    }
    return true;
}

//validForm 함수까지 true값을 받으면 이어서 form submit을 시켜주는  setForm함수
function setForm(editor) {
    var content = editor.getContent();
    $("#daumeditor").val(content)
    return true;
}