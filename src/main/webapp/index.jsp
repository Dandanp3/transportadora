<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <title>Clientes | Dandan Transportes</title>
    <style>
        * { margin: 0; padding: 0; box-sizing: border-box; font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; }
        
        body { background-color: #f8fafc; }

        /* Esse é o truque de ouro do layout! */
        .layout-principal {
            display: flex;
        }

        .conteudo-principal {
            /* Empurra o conteúdo 260px para a direita, para não ficar debaixo do menu fixo */
            margin-left: 260px; 
            padding: 40px;
            width: 100%;
        }

        h1 { color: #1a202c; margin-bottom: 24px; }
    </style>
</head>
<body>

    <div class="layout-principal">
        <jsp:include page="sidebar.jsp" />

        <main class="conteudo-principal">
            
        </main>

    </div>

</body>
</html>