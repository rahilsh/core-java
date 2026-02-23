# java-toolkit
core-java is one stop for core Java features and terminologies

### Java Path
```
/usr/libexec/java_home -V
```

### Copy list to array/modify length of array

```
zuidsArr = zuidList.isEmpty() ? null : Arrays.copyOf( zuidList.toArray() , zuidList.toArray().length, Long[].class ); 
```

### checkIfFileExists

```
checkIfFileExists(AppConfig.REPO_PATH+path+fileName+".txt");
 
private static boolean checkIfFileExists(String filePath){
	File f=newFile(filePath);
	if(f.exists()&&!f.isDirectory()){
		return true;
	}
	return false;
}
```

### Futures
```
thenCompose - Returns a future. Can have async functions
thenApply- Returns a future. Can have sync functions
thenAccept - Returns nothing. Can have sync functions
```

### JMX
```
-Dcom.sun.management.jmxremote -Dcom.sun.management.jmxremote.port=42142 -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.ssl=false
```
