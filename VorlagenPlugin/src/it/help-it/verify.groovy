def logFile = new File( basedir, 'build.log' )
assert logFile.exists()
content = logFile.text

assert content.contains( 'User property: packageName' )

/*
assert content.contains( '[WARNING] COMPILATION WARNING :' )
assert content =~ /\d+ warnings?/
assert content.contains( '1 error' )
*/


