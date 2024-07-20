function consultarRestaurantes() {
    $.ajax({
      url: "http://api.algafoods.local:8080/restaurantes",
      type: "get",
  
      success: function(response) {
        $("#conteudo").text(JSON.stringify(response));
      }
    });
  }
  
  $("#botao").click(consultarRestaurantes);