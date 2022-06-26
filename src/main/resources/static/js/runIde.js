let editor;

window.onload = function(){
    editor = ace.edit("editor");
    editor.setTheme("ace/theme/monokai");
    editor.session.setMode("ace/mode/c_cpp");
    console.log("yes");
}

function changeLanguage(){
    let language = $("languages").val();

    if (language == 'C' || language == 'C++'){
        editor.session.setMode("ace/mode/c_cpp");
    }
    else if(language == 'Java'){
        editor.session.setMode("ace/mode/java");
    }
    else if(language == 'JavaScript'){
        editor.session.setMode("ace/mode/javascript");
    }
    else if(language == 'Python'){
        editor.session.setMode("ace/mode/python");
    }
    else if(language == 'PHP'){
        editor.session.setMode("ace/mode/php");
    }
}
