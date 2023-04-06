$(function(){
    $("#full-name").html(storage.getItem("fullName"));
    if(!isLogin()){
        window.location.replace("http://127.0.0.1:5500/login.html");
    }
})
function isLogin(){
    var  id = storage.getItem("id");
    if(id){
        return true;
    }
    else{
        return false;
    }
}
function logOut(){
    storage.removeItem("id");
    storage.removeItem("fullName");
    storage.removeItem("username");
    storage.removeItem("password");
    window.location.replace("http://127.0.0.1:5500/login.html");
}
function listDepartments(){
    var url = "http://localhost:8080/admin/departments";
    $.ajax({
        url: url,
        type: 'get',
        beforeSend: function (xhr) {
            xhr.setRequestHeader ("Authorization", "Basic " + btoa(storage.getItem("username") + ":" + storage.getItem("password")));
        },
        success: function(result){
        }

    })
}
var departments = [];
var currentPage = 1;
var sizePage = 6;
var sortField = "id";
var isSortType = "asc";
var search;
var minDate;
var maxDate; 
var input = document.getElementById("search-value-input");
input.addEventListener("keypress", function(event) {
  if (event.key === "Enter") {
    event.preventDefault();
    buildTable();
  }
});
function searchDepartment(){
    buildTable();
}
function refeshFilter(){
    resetFilter();
    $("#btn-refesh").css("background-color", "yellow");
    buildTable();
}
function getDepartments(){
    $("#search-value-input").val("");
    $.ajax({
        url: "http://localhost:8080/admin/departments?page="+currentPage+"&size="+sizePage+"&sort="+sortField+","+isSortType,
        type: 'get',
        beforeSend: function (xhr) {
            xhr.setRequestHeader ("Authorization", "Basic " + btoa(storage.getItem("username") + ":" + storage.getItem("password")));
        },
        success: function(result){
            var tr = ""
            $.each(result.content, function(index, item){
                tr+= `<tr>
                    <td><input type="checkbox" name="" id="checkbox-${index}"></td>
                    <td>${item.id}</td>
                    <td>${item.name}</td>
                    <td>${item.creatDate}</td>
                    <td>${item.author.fullName}</td>
                    <td> <i class='fa fa-edit' onclick='showEditDepartment(${item.id})' style="color: yellow;"></i> <i style="color: red;"class='fa fa-trash' onclick='showDeleteDepartment(${item.id},"${item.name}")'></i></td>
                </tr>`;
            })
            $("#show-departments").html(tr);
            departments = result.content;
            pagingTable(result.totalPages);
            $(".fa-sort").show();
            $("#search-control").show();
            $("#btn-search").show();
            search = $("#search-value-input").val();
            $("#input-maxdate").val("");
            $("#input-mindate").val("");
            $("#btn-refesh").show();
            
        },
        error(status){
            alert("Hệ thống máy chủ bị lỗi");
        }

    })
}
function buildTable(){
    var url = "http://localhost:8080/admin/departments?page="+currentPage+"&size="+sizePage+"&sort="+sortField+","+isSortType;
    if(search){
        url+="&search="+search;
    }
    if(minDate){
        url+="&minDate="+minDate;
    }
    if(maxDate){
        url+= "&maxDate="+maxDate;
    }
    $.ajax({
        url: url,
        type: 'get',
        beforeSend: function (xhr) {
            xhr.setRequestHeader ("Authorization", "Basic " + btoa(storage.getItem("username") + ":" + storage.getItem("password")));
        },
        success: function(result){
            var tr = ""
            $.each(result.content, function(index, item){
                tr+= `<tr>
                    <td><input type="checkbox" name="" id="checkbox-${index}"></td>
                    <td>${item.id}</td>
                    <td>${item.name}</td>
                    <td>${item.creatDate}</td>
                    <td>${item.author.fullName}</td>
                    <td> <i class='fa fa-edit' style="color: yellow;" onclick='showEditDepartment(${item.id})'></i> <i style="color: red;" class='fa fa-trash' onclick='showDeleteDepartment(${item.id},"${item.name}")'></i></td>
                </tr>`;
            })
            $("#show-departments").html(tr);
            departments = result.content;
            pagingTable(result.totalPages);
            $(".fa-sort").show();
            $("#search-control").show();
            $("#btn-search").show();
            search = $("#search-value-input").val();
        },
        error(status){
            alert("Hệ thống máy chủ bị lỗi");
        }

    })
}
function getDepartmentsByMinDate(e){
    minDate = e.target.value;
    buildTable();
}
function getDepartmentsByMaxDate(){
    maxDate = $("#input-maxdate").val();
    buildTable();
}
function resetFilter(){
    $("#input-maxdate").val("");
    $("#input-mindate").val("");
    $("#search-value-input").val("");
    minDate = "";
    maxDate = "";
}
function pagingTable(pageAmount){
    var pagingStr ="";
    if(pageAmount>1 && currentPage >1){
        pagingStr+= `<li class="page-item" onclick="previousPage()"><a class="page-link" href="#">Previous</a></li>`;
    }
    for(i=0; i<pageAmount; i++){
        pagingStr+= '<li class="page-item" onclick="changePage('+(i+1)+')"><a style="background-color: '+(currentPage==(i+1)?"yellow":"" )+'" class="page-link" href="#">'+(i+1)+'</a></li>';
        // if(currentPage == i+1){
        //     $(".page-link").css("background", "yellow");
        // }
    }
    if(pageAmount>1 && currentPage < pageAmount){
        pagingStr+= `<li class="page-item" onclick="nextPage(${pageAmount})"><a class="page-link" href="#">Next</a></li>`;
    }
    $("#oke").empty();
    $("#oke").html(pagingStr);

}
function changePage(page){
    if(page == currentPage){
        return;
    }
    currentPage = page;
    buildTable();
}
function previousPage(){
        
    if(currentPage == 1){
        currentPage = 1;
    }
    else{
        currentPage = currentPage -1;
    }
    buildTable();
}
function nextPage(pageAmount){
    
    if(currentPage == pageAmount){
        currentPage = pageAmount;
    }
    else{
        currentPage = currentPage +1;
    }
    buildTable();
}
function sort(nameSortField){
    if(isSortType == "asc"){
        isSortType = "desc";
    }
    else{
        isSortType = "asc";
    }
    sortField = nameSortField;
    buildTable();
}
function resetPage(){
    currentPage = 1;
    sizePage = 6;
}
function exitCreate(){
    // document.getElementById("modal").style.display = "none";
    $("#modal").hide();
    $("#new-name-input").val("");
    $("#showValidationName").hide();
    $("#error-message-update").hide();
}
function createDepartment(){
    $("#title").html("Add new Department");
    $("#modal").show();
    $("#infor-form").hide();
    $("#btn-update").hide();
    $("#input-form").show();
    $("#control-btn").show();
}
function create(){
    var value = $("#new-name-input").val();
    var data = {"name": value, "authorId": storage.getItem("id")};
    if(value.length < 6 || value.length > 50){
        $("#showValidationName").show();
        $("#showValidationName").html("Department name is not valid!")
    }
    else{
        callApiCreateDepartment(data);
    }

}
function callApiCreateDepartment(dataJSON){
    $.ajax({
        type: 'POST',
        url: 'http://localhost:8080/admin/departments',
        data: JSON.stringify(dataJSON),
        contentType: 'application/json; charset=utf-8',
        beforeSend: function (xhr) {
            xhr.setRequestHeader ("Authorization", "Basic " + btoa(storage.getItem("username") + ":" + storage.getItem("password")));
        },
        // dataType: 'json'
        success: function(message, textStatus, xhr){
            showAlert(message);
            buildTable();
            exitCreate();
        },
        error(reponse, textStatus){
            $("#showValidationName").html(reponse.responseJSON.name);
            $("#showValidationName").show();
            if(reponse.status == 403){
                alert("You can not create Department");
            }
            // return;
            // console.log(textStatus);
        }
        
    });
}
function showAlert(message){
    alert(message);
}
function showEditDepartment(id){
    $("#title").html("Edit Department");
    $("#modal").show();
    $("#infor-form").show();
    $("#input-form").hide();
    $("#control-btn").hide();
    $("#btn-update").show();
    var url = "http://localhost:8080/admin/departments/"+id+"";
    $.ajax({
        url: url,
        type: 'get',
        beforeSend: function (xhr) {
            xhr.setRequestHeader ("Authorization", "Basic " + btoa(storage.getItem("username") + ":" + storage.getItem("password")));
        },
        success: function(result){
            $("#name-info").val(result.name);
            $("#author-info").attr("placeholder",result.author.userName);
            $("#date-info").attr("placeholder", result.creatDate);
            $("#btn-update").attr("onclick", "updateDepartment("+result.id+")");
        }

    })
}
function updateDepartment(id){
    var newName = $("#name-info").val();
    data = {"name": newName};
    if(newName.length < 6 || newName.length > 50){
        $("#error-message-update").show();
        $("#error-message-update").html("Department name is not valid!");
    }
    else{
        $.ajax({
            url:  "http://localhost:8080/admin/departments/"+id+"",
            type: 'put',
            data: JSON.stringify(data),
            beforeSend: function (xhr) {
                xhr.setRequestHeader ("Authorization", "Basic " + btoa(storage.getItem("username") + ":" + storage.getItem("password")));
            },
            headers: {
                'x-auth-token': storage.accessToken,
                "Content-Type": "application/json"
            },
            // dataType: 'json',
            success: function(message, textStatus, xhr){
                alert(message);
                buildTable();
                exitCreate();
            },
            error: function(status){
                if(status.status == 403){
                    alert("You can not update Department");
                }
                else{
                    alert("Error System");
                }
            }
        });
    }   
}
function showDeleteDepartment(id,nameDepartment){
    valueDelete = confirm("Do you want to delete Department: "+ nameDepartment);
    if(valueDelete == true){
        $.ajax({
            url: "http://localhost:8080/admin/departments/"+id,
            type: "DELETE", // <- Change here
            contentType: "application/json",
            beforeSend: function (xhr) {
                xhr.setRequestHeader ("Authorization", "Basic " + btoa(storage.getItem("username") + ":" + storage.getItem("password")));
            },
            success: function(message, textStatus, xhr) {
                alert(message);
                buildTable();
            },
            error: function(status) {
                if(status.status == 403){
                    alert("You can not delete Department");
                }
                else{
                    alert("Error System");
                }
            }
        });
    }
}
function deleteListDepartment(){
    var ids = [];
    var i = 0;
    while(true){
        var checkboxItem = document.getElementById("checkbox-"+i);
            if(checkboxItem !== undefined && checkboxItem !== null){
                if(checkboxItem.checked==true){
                    ids.push(departments[i].id);
                }
                i++;
            }
            else{
                break;
            }
    }
    if(ids.length==0){
        alert("Bạn cần chọn Department muốn xoá");
        return;
    }
    var valueDeleteAll = confirm("Do you want to delete "+ ids.length+" Departments");
    if(valueDeleteAll == true){
        $.ajax({
            url: "http://localhost:8080/admin/departments?ids="+ids+"",
            type: "DELETE", // <- Change here
            contentType: "application/json",
            beforeSend: function (xhr) {
                xhr.setRequestHeader ("Authorization", "Basic " + btoa(storage.getItem("username") + ":" + storage.getItem("password")));
            },
            success: function(message, textStatus, xhr) {
                alert(message);
                resetPage();
                buildTable();
            },
            error: function(status) {
                if(status.status == 403){
                    alert("You can not delete Department");
                    var i =0;
                    while(true){
                        var checkboxItem = document.getElementById("checkbox-"+i);
                        if(checkboxItem !== undefined && checkboxItem !== null){
                            if(checkboxItem.checked==true){
                                checkboxItem.checked = false;
                            }
                            i++;
                        }
                        else{
                            break;
                        }
                    }
                }
                else{
                    alert("Error System");
                }
            }
        });
    }
    
}