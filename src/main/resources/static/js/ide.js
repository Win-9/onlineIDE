let editor;

window.onload = function (){
    editor = ace.edit("editor")
    editor.setTheme("ace/theme/monokai");
    editor.session.setMode("ace/mode/c_cpp");
}

function changeLanguage(){
    let language = $("#languages").val();

    if (language == 'c' || language == 'cpp'){
        editor.session.setMode("ace/mode/c_cpp");
    }
    else if(language == 'java'){
        editor.session.setMode("ace/mode/java");
    }
    else if(language == 'js'){
        editor.session.setMode("ace/mode/javascript");
    }
    else if(language == 'py'){
        editor.session.setMode("ace/mode/python");
    }
}

function excuteCode(){
    $.ajax({
        url: "/compile.java",
        method: "POST",

        data: {
            language: $("#languages").val(),
            code: editor.getSession().getValue(),
            projectName: $("#projectName").text()
        },

        success: function (response){
            $(".output").text(response);
        }
    })
}