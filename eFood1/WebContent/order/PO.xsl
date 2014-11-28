<?xml version="1.0"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

  <xsl:template match="/">
    <html>
    <head>
      <title>Order Report</title>
    </head>
    <body>
	<xsl:apply-templates/>
    </body>
    </html>
    </xsl:template>

    <xsl:template match="orderReport">
	<h1>Order Report</h1>
	<h2>Scope</h2>
	<p>Customer name: <xsl:value-of select="@customername"/></p>
	<p>Customer Order: <xsl:value-of select="@ordernumber"/></p>
	<table border="1">
	<xsl:apply-templates/>
	</table>
	</xsl:template>
	
	<xsl:template match="order">
		<tr><td><ul>
			<li><xsl:value-of select="./entry/key/id"/></li>
			<li><xsl:value-of select="./entry/key/name"/></li>
			<li><xsl:value-of select="./entry/key/price"/></li>
			<li><xsl:value-of select="./entry/value"/></li>
		</ul></td></tr>
	</xsl:template>
	
</xsl:stylesheet>
