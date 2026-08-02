const ctx2 = document.getElementById("graficoPrestamos");

new Chart(ctx2, {

    type: "bar",

    data: {

        labels: [

            "Prestados",
            "Devueltos"

        ],

        datasets: [{

            label: "Cantidad",

            data: [

                prestamosActivos,
                prestamosDevueltos

            ],

            backgroundColor: [

                "#0077b6",
                "#90e0ef"

            ]

        }]

    },

    options: {

        responsive: true,
        maintainAspectRatio: false

    }

});

 const ctx = document.getElementById("graficoLibros");

 new Chart(ctx, {

     type: "doughnut",

     data: {

         labels: [

             "Disponibles",
             "Prestados"

         ],

         datasets: [{

             data: [

                 disponibles,
                 prestados

             ],

             backgroundColor: [

                 "#00b4d8",
                 "#03045e"

             ]

         }]

     },

     options: {

         responsive: true,
         maintainAspectRatio: false,
         plugins: {

             legend: {

                 position: "bottom"

             }

         }

     }

 });