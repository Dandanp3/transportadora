(function () {
    "use strict";

    function semAcento(s) { // NFD separa as letras dos acentos
        // regex com intervalo de caracteres unicode
        return s.normalize("NFD").replace(/[\u0300-\u036f]/g, "").toLowerCase(); //
    }

    function somenteDigitos(s) {
        // procura tudo q nao for digito - D
        return s.replace(/\D/g, "");
    }

    function iniciar(campo) {
        var tabela = document.getElementById(campo.dataset.buscaTabela);
        if (!tabela || !tabela.tBodies.length) return;

        var corpo = tabela.tBodies[0];
        var modo = campo.dataset.buscaModo || "texto";
        var colunas = (campo.dataset.buscaColunas || "")
            .split(",") // dividir em partes apos virgula
            .filter(function (c) { return c.trim() !== ""; }) // tira espaços dos cantos
            .map(Number); // converte os elementos strings em numero

        // enter nao da f5
        var form = campo.closest("form");
        if (form) {
            form.addEventListener("submit", function (e) { e.preventDefault(); }); // funçao para tira comportamento comum do mnaavegador, nesse caso o f5
        }

        // linha nenhum resultado
        var semResultado = document.createElement("tr");
        var celula = document.createElement("td");
        celula.colSpan = tabela.querySelectorAll("thead th").length || 1;
        celula.textContent = campo.dataset.buscaVazio || "Nenhum resultado encontrado para essa busca.";
        celula.style.cssText = "text-align: center; padding: 32px; color: #475569;";
        semResultado.appendChild(celula);
        semResultado.style.display = "none";
        corpo.appendChild(semResultado);

        campo.addEventListener("input", function () {
            filtrar(campo, corpo, semResultado, modo, colunas);
        });
    }

    // percorre as linhas e ve oq fica visivel
    function filtrar(campo, corpo, semResultado, modo, colunas) {
        var bruto = campo.value.trim();
        var termo = modo === "numerico" ? somenteDigitos(bruto) : semAcento(bruto);
        var total = 0;
        var achados = 0;

        Array.prototype.forEach.call(corpo.rows, function (linha) {
            // pula as linhas de aviso 
            if (linha === semResultado || linha.querySelector("td[colspan]")) return;

            total++;
            var mostrar = bruto === "" || combina(linha, termo, modo, colunas);
            linha.style.display = mostrar ? "" : "none";
            if (mostrar) achados++;
        });

        semResultado.style.display = (bruto !== "" && total > 0 && achados === 0) ? "" : "none";
    }

    // correspondendo a pesquisa a cada coluna
    function combina(linha, termo, modo, colunas) {
        var celulas = Array.prototype.slice.call(linha.cells);
        if (colunas.length) {
            celulas = colunas.map(function (i) { return linha.cells[i]; }).filter(Boolean);
        }

        return celulas.some(function (td) {
            if (termo === "") return false;
            var conteudo = modo === "numerico" ? somenteDigitos(td.textContent) : semAcento(td.textContent);
            return conteudo.indexOf(termo) !== -1;
        });
    }

    document.addEventListener("DOMContentLoaded", function () {
        document.querySelectorAll("input[data-busca-tabela]").forEach(iniciar);
    });
})();