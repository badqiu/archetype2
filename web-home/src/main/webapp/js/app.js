function disableSubmit(finalResult,submitButtonId) {
	if(finalResult) {
		document.getElementById(submitButtonId).disabled = true;
		return finalResult;
	}else {
		return finalResult;
	}
}

function batchDelete(action,checkboxName,form){
    if (!hasOneChecked(checkboxName)){
            alert('请选择要操作的对象!');
            return;
    }
    if (confirm('确定执行[删除]操作?')){
        form.action = action;
        form.submit();
    }
}

function hasOneChecked(name){
    var items = document.getElementsByName(name);
    if (items.length > 0) {
        for (var i = 0; i < items.length; i++){
            if (items[i].checked == true){
                return true;
            }
        }
    } else {
        if (items.checked == true) {
            return true;
        }
    }
    return false;
}

function setAllCheckboxState(name,state) {
	var elms = document.getElementsByName(name);
	for(var i = 0; i < elms.length; i++) {
		elms[i].checked = state;
	}
}

function getReferenceForm(elm) {
	while(elm && elm.tagName != 'BODY') {
		if(elm.tagName == 'FORM') return elm;
		elm = elm.parentNode;
	}
	return null;
}


//init validator
/*
jQuery.validator.setDefaults({
    highlight: function(element) {
        $(element).closest('.form-group').addClass('has-error');
        $(element).closest('.form-group').removeClass('has-success');
    },
    unhighlight: function(element) {
        $(element).closest('.form-group').removeClass('has-error');
        $(element).closest('.form-group').addClass('has-success');
    },
    errorElement: 'span',
    errorClass: 'help-block',
    errorPlacement: function(error, element) {
        if(element.parent('.input-group').length) {
            error.insertAfter(element.parent());
        } else {
            error.insertAfter(element);
        }
    }
});	
*/

jQuery.validator.setDefaults({
    highlight: function (element, errorClass, validClass) {
        if (element.type === "radio") {
            this.findByName(element.name).addClass(errorClass).removeClass(validClass);
        } else {
            $(element).closest('.form-group').removeClass('has-success has-feedback').addClass('has-error has-feedback');
            $(element).closest('.form-group').find('i.fa').remove();
            $(element).after('<i class="fa glyphicon glyphicon-remove-circle form-control-feedback"></i>');
        }
    },
    unhighlight: function (element, errorClass, validClass) {
        if (element.type === "radio") {
            this.findByName(element.name).removeClass(errorClass).addClass(validClass);
        } else {
            $(element).closest('.form-group').removeClass('has-error has-feedback').addClass('has-success has-feedback');
            $(element).closest('.form-group').find('i.fa').remove();
            $(element).after('<i class="fa glyphicon glyphicon-ok-circle form-control-feedback"></i>');
        }
    },
    errorElement: 'span',
    errorClass: 'help-block',
    errorPlacement: function(error, element) {
        if(element.parent('.input-group').length) {
            error.insertAfter(element.parent());
        } else {
            error.insertAfter(element);
        }
    }
});


/**
 * api for RESTful operation
 */

/**
 * use case: <a href="/user/12" onclick="doRestDelete(this,'confirm delete?');return false;">delete</a>
 */
function doRestDelete(anchor,confirmMsg) {
	if (confirmMsg && confirm(confirmMsg)) {
		var form = document.createElement("form");
		form.style.display = "none";
		anchor.parentNode.appendChild(form);
		form.method = "POST";
		form.action = anchor.href;
		var m = document.createElement("input");
		m.setAttribute("type", "hidden");
		m.setAttribute("name", "_method");
		m.setAttribute("value", "delete");
		form.appendChild(m);
		form.submit();
	}
}

function doRestBatchDelete(action,checkboxName,form) {
	if (!hasOneChecked(checkboxName)) {
		alert("请选择你要删除的对象!");
		return;
	}
	if (confirm("你确认要删除?")) {
		form.action = action;
		form.method = 'POST';
		
		var m = document.createElement("input");
		m.setAttribute("type", "hidden");
		m.setAttribute("name", "_method");
		m.setAttribute("value", "delete");
		form.appendChild(m);
		
		form.submit();
	}
}

