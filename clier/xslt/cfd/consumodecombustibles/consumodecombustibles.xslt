<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xs="http://www.w3.org/2001/XMLSchema" xmlns:fn="http://www.w3.org/2005/xpath-functions" xmlns:consumodecombustibles="http://www.sat.gob.mx/ConsumoDeCombustibles">

  <xsl:template match="consumodecombustibles:ConsumoDeCombustibles">
    <!--Manejador de nodos tipo ConsumoDeCombustibles-->
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@version" />
    </xsl:call-template>
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@tipoOperacion" />
    </xsl:call-template>
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@numeroDeCuenta" />
    </xsl:call-template>
    <xsl:call-template name="Opcional">
      <xsl:with-param name="valor" select="./@subTotal" />
    </xsl:call-template>
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@total" />
    </xsl:call-template>

    <!--  Iniciamos el manejo de los elementos hijo en la secuencia -->
    <xsl:apply-templates select="./consumodecombustibles:Conceptos" />
  </xsl:template>


  <xsl:template match="consumodecombustibles:Conceptos">
    <!--  Iniciamos el tratamiento de los atributos de consumodecombustibles:ConceptoConsumoDeCombustibles-->
    <xsl:for-each select="./consumodecombustibles:ConceptoConsumoDeCombustibles">
      <xsl:apply-templates select="."/>
    </xsl:for-each>
    
  </xsl:template>

  <!--  Iniciamos el manejo de los elementos hijo en la secuencia ConceptoConsumoDeCombustibles-->
  <xsl:template match="consumodecombustibles:ConceptoConsumoDeCombustibles">
    <!--  Iniciamos el manejo de los nodos dependientes -->
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@identificador" />
    </xsl:call-template>
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@fecha" />
    </xsl:call-template>
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@rfc" />
    </xsl:call-template>
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@claveEstacion" />
    </xsl:call-template>
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@tipoCombustible" />
    </xsl:call-template>
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@cantidad" />
    </xsl:call-template>
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@nombreCombustible" />
    </xsl:call-template>
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@folioOperacion" />
    </xsl:call-template>
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@valorUnitario" />
    </xsl:call-template>
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@importe" />
    </xsl:call-template>

    <!--  Iniciamos el manejo de los elementos hijo en la secuencia -->
    <xsl:apply-templates select="./consumodecombustibles:Determinados" />

  </xsl:template>

    <xsl:template match="consumodecombustibles:Determinados">
    <!--  Iniciamos el tratamiento de los atributos de consumodecombustibles:Determinado-->
    <xsl:for-each select="./consumodecombustibles:Determinado">
      <xsl:apply-templates select="."/>
    </xsl:for-each>
  </xsl:template>

  
  <!--  Iniciamos el manejo de los elementos hijo en la secuencia Determinado-->
  <xsl:template match="consumodecombustibles:Determinado">
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@impuesto" />
    </xsl:call-template>
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@tasaOCuota" />
    </xsl:call-template>
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@importe" />
    </xsl:call-template>
  </xsl:template>
</xsl:stylesheet>