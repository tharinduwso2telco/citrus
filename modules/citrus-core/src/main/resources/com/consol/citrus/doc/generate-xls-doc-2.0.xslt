<?xml version="1.0" encoding="ISO-8859-1"?>
<!-- 
 Copyright 2006-2009 ConSol* Software GmbH.
 
 This file is part of Citrus.
 
   Citrus is free software: you can redistribute it and/or modify
   it under the terms of the GNU General Public License as published by
   the Free Software Foundation, either version 3 of the License, or
   (at your option) any later version.
 
   Citrus is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
   GNU General Public License for more details.
 
   You should have received a copy of the GNU General Public License
   along with Citrus.  If not, see <http://www.gnu.org/licenses/>.
-->
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:spring="http://www.springframework.org/schema/beans" 
	xmlns:tsf="http://www.citrusframework.org/schema/testcase"
	xmlns="urn:schemas-microsoft-com:office:spreadsheet"
	xmlns:ss="urn:schemas-microsoft-com:office:spreadsheet"
	exclude-result-prefixes="ss tsf spring">

	<xsl:output method="xml" version="1.0" encoding="UTF-8" indent="yes" omit-xml-declaration="yes"/>

	<xsl:template match="/spring:beans">
		<xsl:apply-templates select="tsf:testcase" />
	</xsl:template>

	<xsl:template match="/spring:beans/tsf:testcase">
		<Cell>
			<Data ss:Type="String">
				<xsl:value-of select="@name" />
			</Data>
			<NamedCell ss:Name="_FilterDatabase"/>
		</Cell>
		
		<Cell>
			<Data ss:Type="String">
				<xsl:value-of select="tsf:meta-info/tsf:author" />
			</Data>
			<NamedCell ss:Name="_FilterDatabase"/>
		</Cell>
		
		<Cell>
			<Data ss:Type="String">
				<xsl:value-of select="tsf:meta-info/tsf:status" />
			</Data>
			<NamedCell ss:Name="_FilterDatabase"/>
		</Cell>

		<Cell>
			<Data ss:Type="String">
				<xsl:value-of select="normalize-space(tsf:description)" />
			</Data>
			<NamedCell ss:Name="_FilterDatabase"/>
		</Cell>				
		
		<Cell>
			<Data ss:Type="String">
				<xsl:value-of select="tsf:meta-info/tsf:creationdate" />
			</Data>
			<NamedCell ss:Name="_FilterDatabase"/>
		</Cell>
	</xsl:template>
	
	<xsl:template match="text()" />

</xsl:stylesheet>