## Rest api File Download ( file created with data from database)
> First of all, make a header row. 
and add body rows inserting data. 
At last, return converting the file to byte[].

### CSV download (Apache commons-csv)
> Add in build.gradle. 

```bash
compile ('org.apache.commons:commons-csv:1.9.0')
```

### Excel download (Apache POI(Poor Obfuscation Implement))
> Add in build.gradle. 

```bash
compile('org.apache.poi:poi:3.17')
compile('org.apache.poi:poi-ooxml:3.17')
```
