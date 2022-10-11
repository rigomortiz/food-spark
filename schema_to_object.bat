@if (@CodeSection == @Batch) @then
@echo off & setlocal
cscript /nologo /e:JScript "%~f0" %*
goto :EOF

@end // end batch / begin JScript hybrid chimera

var htmlfile = WSH.CreateObject('htmlfile');
htmlfile.write('<meta http-equiv="x-ua-compatible" content="IE=9" />');
var JSON = htmlfile.parentWindow.JSON;

//needs file existence checks
var jsloc=WScript.Arguments.Item(0);


FSOObj = new ActiveXObject("Scripting.FileSystemObject");
fs = new ActiveXObject("Scripting.FileSystemObject")
var txtFile=FSOObj.OpenTextFile(jsloc,1);
var json=txtFile.ReadAll();
WScript.Echo("Schema: " + jsloc + "\n");
try {
	var jParsed=JSON.parse(json);
}catch(err) {
   htmlfile.close();
   txtFile.close();
   WScript.Exit(1);
   //WScript.Echo(err.message);
}
var FILE_EXTESION = ".scala";
var FIELDS_KEY = "fields";
var NAME_KEY = "name";
var CLASS_NAME = camelCase(jParsed[NAME_KEY]);
var OBJECT_STRING = 'object {NAME_OBJECT} {\n{COLUMNS}\n}'
var COLUMN_STRING = '  case object {NAME} extends Column {\n    override val name: String = \"{VALUE}\"\n  }\n'
function camelCase(str) {
  var split = str.split("_")
  var name = ''
  for(var i = 0; i < split.length; i++) {
    var word = split[i].substr(0,1).toUpperCase() + split[i].substr(1).toLowerCase();
    name += word;
  }
  return name;
}
function createClassObject(json) {
  var columns = '';
  for(var i = 0; i < json[FIELDS_KEY].length; i++) {
    var name = json[FIELDS_KEY][i][NAME_KEY];
    var col = COLUMN_STRING.replace('{NAME}', camelCase(name)).replace('{VALUE}', json[FIELDS_KEY][i][NAME_KEY]);
    columns += col;
  }
  return OBJECT_STRING.replace("{NAME_OBJECT}", CLASS_NAME).replace('{COLUMNS}', columns);
}

data = createClassObject(jParsed);
a = fs.CreateTextFile(CLASS_NAME + FILE_EXTESION, true)
a.WriteLine(data)
a.close()
htmlfile.close();
txtFile.close();
WScript.Echo(data);
WScript.Echo("\nDone");
WScript.Echo("Class object: " + CLASS_NAME + FILE_EXTESION);
