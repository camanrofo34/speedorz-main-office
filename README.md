<h1>AUTO-UPB Backend -  Speedorz 🚗</h1>

<h2>Descripción General</h2>
<p>
  Este proyecto corresponde al backend del sistema de la empresa <strong>Speedorz</strong>, casa matriz del consorcio <strong>AUTO-UPB</strong>, desarrollado con <strong>Spring Boot</strong>. La solución busca integrar los procesos de negocio establecidos por el consorcio, conformado por cinco empresas del sector automotriz del Área Metropolitana de Bucaramanga, compartiendo operaciones relacionadas con la venta de vehículos, repuestos y accesorios, así como servicios de mantenimiento y atención al cliente.
</p>
<p>
  La aplicación es parte de una arquitectura <strong>cliente-servidor</strong>, respaldada por una base de datos relacional y diseñada para garantizar los principios de seguridad de la información: <strong>confidencialidad</strong>, <strong>integridad</strong>, <strong>disponibilidad</strong>, <strong>autenticación</strong> y <strong>no repudio</strong>, incluyendo la trazabilidad de todas las acciones de los usuarios.
</p>

<h2>🛠️ Tecnologías Utilizadas</h2>
<ul>
  <li>Java 21</li>
  <li>Spring Boot</li>
  <li>Spring Data JPA</li>
  <li>MySQL</li>
  <li>Maven</li>
  <li>SMTP (correo electrónico)</li>
  <li>VoIP</li>
  <li>HTTPS / FTPS</li>
</ul>

<h2>⚙️ Módulos del Sistema</h2>

<h3>✅ Administración de Inventario</h3>
<p>Gestiona los ítems del inventario garantizando tiempos de respuesta menores a 6 segundos y trazabilidad completa.</p>

<h4>Funcionalidades</h4>
<ul>
  <li>Registro, actualización y eliminación de ítems.</li>
  <li>Búsqueda de ítems por nombre.</li>
  <li>Consulta de detalle de ítems.</li>
  <li>Creación de órdenes de compra.</li>
  <li>Emisión de facturas de venta.</li>
  <li>Registro de entradas y salidas del inventario.</li>
  <li>Movimientos entre dependencias.</li>
  <li>Informes de inventario y disponibilidad.</li>
  <li>Gestión de devoluciones y garantías.</li>
  <li>Reportes financieros: Histórico de precios, Pérdidas y ganancias, Cuentas por pagar y cobrar.</li>
</ul>

<h4>Estructura de datos mínima</h4>
<table>
  <tr>
    <th>Entidad</th>
    <th>Campos obligatorios</th>
  </tr>
  <tr>
    <td>Ítem</td>
    <td>Identificador, nombre, descripción, stock, precio de venta</td>
  </tr>
  <tr>
    <td>Orden de compra</td>
    <td>Identificador, vendedor, cliente, responsable, fecha, ítems, subtotal, descuentos, impuestos, total</td>
  </tr>
  <tr>
    <td>Factura</td>
    <td>Identificador, datos de empresa, fecha, cliente, ítems, subtotal, descuentos, impuestos, total</td>
  </tr>
</table>

<h3>✅ Administración de Usuarios</h3>
<p>Gestiona los usuarios del sistema garantizando autenticación segura y trazabilidad para el no repudio de las acciones.</p>

<h4>Funcionalidades</h4>
<ul>
  <li>Registro, actualización y eliminación de usuarios.</li>
  <li>Búsqueda de usuarios por nombre o identificador.</li>
  <li>Gestión de estados: activo, inactivo, fuera de servicio.</li>
  <li>Roles: Administrador de usuarios y Usuario de empresa.</li>
</ul>

<h4>Estructura de datos mínima</h4>
<table>
  <tr>
    <th>Campo</th>
    <th>Descripción</th>
  </tr>
  <tr>
    <td>Identificador</td>
    <td>Único por empresa y sistema</td>
  </tr>
  <tr>
    <td>Nombre de usuario</td>
    <td>Correo electrónico corporativo</td>
  </tr>
  <tr>
    <td>Nombres y apellidos</td>
    <td>Nombre completo del usuario</td>
  </tr>
  <tr>
    <td>Cédula</td>
    <td>Documento de identidad</td>
  </tr>
  <tr>
    <td>Dirección</td>
    <td>Dirección de residencia o trabajo</td>
  </tr>
  <tr>
    <td>Teléfono</td>
    <td>Número de contacto</td>
  </tr>
</table>

<h3>✅ Administración de Clientes</h3>
<p>Gestiona los datos de clientes naturales y jurídicos para los procesos de ventas y postventa del consorcio.</p>

<h4>Funcionalidades</h4>
<ul>
  <li>Registro, actualización y eliminación de clientes.</li>
  <li>Búsqueda de clientes por nombre o identificador.</li>
</ul>

<h4>Estructura de datos mínima</h4>
<table>
  <tr>
    <th>Campo</th>
    <th>Descripción</th>
  </tr>
  <tr>
    <td>Identificador</td>
    <td>Único para cada cliente</td>
  </tr>
  <tr>
    <td>Nombre/Razón Social</td>
    <td>Cliente natural o jurídico</td>
  </tr>
  <tr>
    <td>Cédula/NIT</td>
    <td>Documento de identificación</td>
  </tr>
  <tr>
    <td>Dirección</td>
    <td>Ubicación física del cliente</td>
  </tr>
  <tr>
    <td>Teléfono</td>
    <td>Número de contacto</td>
  </tr>
</table>

<h2>🔐 Seguridad y Cumplimiento Legal</h2>
<p>
  El sistema cumple con la normativa colombiana vigente sobre protección de datos personales:
  <ul>
    <li>Ley 1581 de 2012</li>
    <li>Decreto 1081 de 2015</li>
  </ul>
</p>
<p>
  <strong>Medidas implementadas:</strong>
</p>
<ul>
  <li>Autenticación segura por contraseña.</li>
  <li>Roles de usuario con permisos diferenciados.</li>
  <li>Registro y auditoría de actividades.</li>
  <li>Comunicación cifrada mediante HTTPS/FTPS.</li>
  <li>Integración de correo corporativo y VoIP como canales oficiales del consorcio.</li>
</ul>

<h2>💾 Respaldo y Configuración</h2>
<ul>
  <li>Copias de seguridad programadas.</li>
  <li>Procedimientos de restauración.</li>
  <li>Configuración personalizable por empresa.</li>
</ul>

<h2>🌐 Comunicación Interempresarial</h2>
<ul>
  <li>Correo electrónico (SMTP): notificaciones, campañas y gestión de procesos compartidos.</li>
  <li>VoIP: comunicación síncrona para soporte y coordinación.</li>
</ul>

<h2>🚀 Requisitos para Despliegue</h2>
<ul>
  <li>Java 21</li>
  <li>MySQL</li>
  <li>Servidor SMTP y VoIP configurados</li>
  <li>Infraestructura de red con servicios: DHCP, DNS, NAT, HTTPS/FTPS</li>
</ul>

<h2>📄 Licencia</h2>
<p>Este proyecto es de uso interno del consorcio <strong>AUTO-UPB</strong> y está sujeto a las políticas establecidas por las empresas participantes.</p>

