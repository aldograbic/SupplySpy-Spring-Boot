window.addEventListener("load", () => {
  fetch("/api/chart-data")
    .then((response) => response.json())
    .then((data) => {
      const { lineChart } = data;

      buildChart(
        "#hs-single-line-chart",
        (mode) => ({
          chart: {
            height: 250,
            type: "line",
            toolbar: {
              show: true,
            },
            zoom: {
              enabled: false,
            },
          },
          series: [
            {
              name: "Sales",
              data: lineChart.salesData,
            },
          ],
          dataLabels: {
            enabled: false,
          },
          stroke: {
            curve: "straight",
            width: [4, 4, 4],
            dashArray: [0, 0, 4],
          },
          title: {
            show: false,
          },
          legend: {
            show: false,
          },
          grid: {
            strokeDashArray: 0,
            borderColor: "#e5e7eb",
            padding: {
              top: -20,
              right: 0,
            },
          },
          xaxis: {
            type: "category",
            categories: lineChart.categories,
            axisBorder: {
              show: false,
            },
            axisTicks: {
              show: false,
            },
            tooltip: {
              enabled: false,
            },
            labels: {
              offsetY: 5,
              style: {
                colors: "#9ca3af",
                fontSize: "13px",
                fontFamily: "Inter, ui-sans-serif",
                fontWeight: 400,
              },
              formatter: (title) => {
                let formattedTitle = title;

                if (formattedTitle) {
                  const parts = formattedTitle.split(" ");
                  if (parts.length > 1) {
                    formattedTitle = `${parts[0]} ${parts[1].slice(0, 3)}`;
                  } else {
                    formattedTitle = parts[0];
                  }
                }

                return formattedTitle;
              },
            },
          },
          yaxis: {
            min: 0,
            max: lineChart.yAxisMax,
            tickAmount: 4,
            labels: {
              align: "left",
              minWidth: 0,
              maxWidth: 140,
              style: {
                colors: "#9ca3af",
                fontSize: "12px",
                fontFamily: "Inter, ui-sans-serif",
                fontWeight: 400,
              },
              formatter: (value) =>
                value >= 1000 ? `${value / 1000}k` : value,
            },
          },
          tooltip: {
            custom: function (props) {
              const { categories } = props.ctx.opts.xaxis;
              const { dataPointIndex } = props;
              const title = categories[dataPointIndex].split(" ");
              const newTitle = `${title[0]} ${title[1]}`;

              return buildTooltip(props, {
                title: newTitle,
                mode,
                hasTextLabel: true,
                wrapperExtClasses: "min-w-28",
                labelDivider: ":",
                labelExtClasses: "ms-2",
              });
            },
          },
        }),
        {
          colors: ["#2563EB", "#22d3ee", "#d1d5db"],
          grid: {
            borderColor: "#e5e7eb",
          },
        },
        {
          colors: ["#3b82f6", "#22d3ee", "#6b7280"],
          grid: {
            borderColor: "#374151",
          },
        }
      );
    })
    .catch((error) => console.error("Error fetching chart data:", error));

  buildChart(
    "#hs-doughnut-chart",
    () => ({
      chart: {
        height: 230,
        width: 230,
        type: "donut",
        zoom: {
          enabled: false,
        },
      },
      plotOptions: {
        pie: {
          donut: {
            size: "50%",
          },
        },
      },
      series: [47, 23],
      labels: ["Credit card", "Cash"],
      legend: {
        show: false,
      },
      dataLabels: {
        enabled: false,
      },
      stroke: {
        width: 5,
      },
      grid: {
        padding: {
          top: -6,
          bottom: -2,
          left: -6,
          right: -6,
        },
      },
      states: {
        hover: {
          filter: {
            type: "none",
          },
        },
      },
    }),
    {
      colors: ["#F99932", "#22d3ee"],
      stroke: {
        colors: ["rgb(255, 255, 255)"],
      },
    },
    {
      colors: ["#F99932", "#22d3ee"],
      stroke: {
        colors: ["rgb(38, 38, 38)"],
      },
    }
  );
});
