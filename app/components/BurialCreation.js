function createBurial() {
    // ... существующий код ...
    
    // Добавление функции для выделения четырех точек
    function selectPoints(points) {
        if (points.length === 4) {
            drawPolygon(points);
        } else {
            alert("Пожалуйста, выберите ровно 4 точки.");
        }
    }

    function drawPolygon(points) {
        // Логика для рисования четырехугольника на карте
        // ...
    }

    // ... существующий код ...
} 