const uploadButton1 = document.querySelector('.browse-btn1');
const uploadButton2 = document.querySelector('.browse-btn2');
const sendButton = document.querySelector('.send-btn');
var patientFileInfo = document.querySelector('.patient-file-info');
var reportFileInfo = document.querySelector('.report-file-info');
var patientFile = document.getElementById('patient-file');
var reportFile = document.getElementById('report-file');
const validate = document.getElementById('validate');
var file_1,file_2;

var reloadVariables = function(){

  document.getElementById('patient-file').value="";
  document.getElementById('report-file').value="";
  file_1="";
  file_2="";
  patientFileInfo.innerHTML="Select Patient's Information";
  reportFileInfo.innerHTML="Select Report Document";
}

uploadButton1.addEventListener('click', (e) => {
  patientFile.click();
});

uploadButton2.addEventListener('click', (e) => {
    reportFile.click();

  });

patientFile.addEventListener('change', () => {
  file_1=patientFile.files;

  if(patientFile.files.length===0){
    patientFileInfo.innerHTML="Select Patient's Information";
  }else{
    const name = patientFile.value.split(/\\|\//).pop();
    const truncated = name.length > 20 
      ? (name.substr(0,20)+"...") 
      : name;
    patientFileInfo.innerHTML = `<span data-toggle="tooltip" title=${name} style="cursor:pointer;">${truncated}</span>`;;
  
  }
});

reportFile.addEventListener('change', () => {

  file_2=reportFile.files;

  if(reportFile.files.length===0){
    reportFileInfo.innerHTML="Select Report Document";
  }else{
    const name = reportFile.value.split(/\\|\//).pop();
    const truncated = name.length > 20 
      ? (name.substr(0,20)+"...") 
      : name;
        
    reportFileInfo.innerHTML = `<span data-toggle="tooltip" title=${name} style="cursor:pointer;">${truncated}</span>`;
  
  }
});

sendButton.addEventListener('click',function(event){
    if(file_1 !== undefined && file_2 !== undefined && file_2.length !== 0 && file_1.length !== 0){
        
        //console.log(file_1[0].name)
        //console.log(file_2[0].name)
        
        validate.innerHTML="";
        var xhttp = new XMLHttpRequest();
        xhttp.onreadystatechange = function() {
          if (this.readyState == 4 && this.status == 200) {
            console.log("File recieved on the other hand");
            reloadVariables();
          }
        };
        xhttp.open("POST", "http://localhost:8080/view", true);
        xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        
    		var formData = new FormData();
        formData.append("file1", patientFile.files[0]);
        formData.append("file2", reportFile.files[0]);
        
        xhttp.send(formData);
        //reloadVariables();
    }
    else{
      validate.innerHTML = "*Please upload both the files";
      
    }
});
