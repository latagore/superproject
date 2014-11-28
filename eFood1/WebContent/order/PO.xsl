<?xml version="1.0"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

  <xsl:template match="/">
    <html>
    <head>
      <title>Order Report</title>
    </head>
    <body>
		<h1>Order Report</h1>
		<h2>Scope</h2>
		<p>Order id: <xsl:value-of select="order/@id"/></p>
		<p>Order placed on: <xsl:value-of select="order/@submitted"/></p>
		<p>Customer Name: <xsl:value-of select="order/customer/name"/></p>
		<p>Customer Account: <xsl:value-of select="order/customer/@account"/></p>
		<table border="1">
			<tr>
				<td>Item Name</td>
				<td>Item Price</td>
				<td>Quantity</td>
				<td>Total</td>
			</tr>
			<xsl:apply-templates select="order/items/item"/>
		</table>
    </body>
    </html>
    </xsl:template>
    <xsl:template match="/order/items/item">
			<tr>
				<td><xsl:value-of select="./name"/></td>
				<td>$<xsl:value-of select="format-number(./price, '#.00')"/></td>
				<td><xsl:value-of select="./quantity"/></td>
				<td>$<xsl:value-of select="format-number(./extended, '#.00')"/></td>
			</tr>
	</xsl:template>
</xsl:stylesheet>
