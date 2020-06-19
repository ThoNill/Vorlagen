def logFile = new File( basedir, 'build.log' )
assert logFile.exists()
content = logFile.text

assert !(content =~ /symbol: class (?!Embeddable)/)

assert content.contains( 'erzeugt\\freeform\\zumTest\\TestAdresse.txt')
