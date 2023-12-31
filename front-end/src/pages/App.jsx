import { useState } from "react";
import { Route, Routes } from "react-router-dom";
import { UserLayout } from "../layouts/UserLayout";
import HomePage from "./Customer/HomePage";
import SignIn from "./Customer/SignIn";
import SignUp from "./Customer/SignUp";
import MovieSchedule from "./Customer/MovieSchedule";
import { AdminLayout } from "../layouts/AdminLayout";
import AdminIndex from "./Admin/AdminIndex";
import MovieManagement from "./Admin/MovieManagement";
import MovieManagementEdit from "./Admin/MovieManagementEdit";
import CinemaManagement from "./Admin/CinemaManagement";
import CinemaManagementAdd from "./Admin/CinemaManagementAdd";
import CinemaManagementEdit from "./Admin/CinemaManagementEdit";
import NotFound from "./error/NotFound";
import ProductManagement from "./Admin/ProductManagement";

import ShowtimeManagement from "./Admin/ShowtimeManagement";
import EmployeeManagement from "./Admin/EmployeeManagement";
import OrderManagement from "./Admin/OrderManagement";
import { UserContextProvider } from "../components/UserContext";
import MovieDetail from "./Customer/MovieDetail";
import ProductManagementEdit from "./Admin/ProductManagementEdit";
import RoomManagement from "./Admin/RoomManagement";
import RoomManagementEdit from "./Admin/RoomManagementEdit";
import ShowtimeManagementEdit from "./Admin/ShowtimeManagementEdit";
import BookingTicket from "./Customer/BookingTicket";
import UserOrders from "./Customer/UserOrders";
import OrderDetail from "./Customer/OrderDetail";
import AdminSignIn from "./Admin/AdminSignIn";
import Search from "./Customer/Search";

function App() {
  return (
    <>
      <UserContextProvider>
        <Routes>
          <Route path="/default" element={<UserLayout />}>
            <Route index element={<HomePage />} />
            <Route path="/default/login" element={<SignIn />} />
            <Route path="/default/register" element={<SignUp />} />
            <Route
              path="/default/showing"
              element={<MovieSchedule movieStatus="SHOWING" />}
            />
            <Route
              path="/default/coming"
              element={<MovieSchedule movieStatus="COMING" />}
            />
            <Route path="/default/movies/:id" element={<MovieDetail />} />
            <Route
              path="/default/movies/booking-ticket/:id"
              element={<BookingTicket />}
            />
            <Route path="/default/user-orders" element={<UserOrders />}></Route>
            <Route
              path="/default/user-orders/view/:id"
              element={<OrderDetail />}
            />
            <Route path="/default/result" element={<Search />} />
          </Route>
          <Route path="/admin" element={<AdminLayout />}>
            <Route index element={<AdminIndex />} />
            <Route path="/admin/login" element={<AdminSignIn />} />
            <Route
              path="/admin/movie-management"
              element={<MovieManagement />}
            />
            <Route
              path="/admin/movie-management/edit"
              element={<MovieManagementEdit />}
            />
            <Route
              path="/admin/movie-management/edit/:id"
              element={<MovieManagementEdit />}
            />

            <Route
              path="/admin/cinema-management"
              element={<CinemaManagement />}
            />
            <Route
              path="/admin/cinema-management/add"
              element={<CinemaManagementAdd />}
            />
            <Route
              path="/admin/cinema-management/edit/:id"
              element={<CinemaManagementEdit />}
            />
            <Route
              path="/admin/cinema/:id/room-management"
              element={<RoomManagement />}
            />
            <Route
              path="/admin/cinema/:id/room-management/edit/"
              element={<RoomManagementEdit />}
            />
            <Route
              path="/admin/cinema/:id/room-management/edit/:roomId"
              element={<RoomManagementEdit />}
            />
            <Route
              path="/admin/product-management"
              element={<ProductManagement />}
            />
            <Route
              path="/admin/product-management/edit"
              element={<ProductManagementEdit />}
            />
            <Route
              path="/admin/product-management/edit/:id"
              element={<ProductManagementEdit />}
            />
            <Route
              path="/admin/employee-management"
              element={<EmployeeManagement />}
            />
            <Route
              path="/admin/showtime-management"
              element={<ShowtimeManagement />}
            />
            <Route
              path="/admin/cinema/:choosenCineId/showtimes/edit/:id"
              element={<ShowtimeManagementEdit />}
            />
            <Route
              path="/admin/cinema/:choosenCineId/showtimes/edit/"
              element={<ShowtimeManagementEdit />}
            />
            <Route
              path="/admin/orders-management"
              element={<OrderManagement />}
            />
          </Route>
          <Route path="*" element={<NotFound />} />
        </Routes>
      </UserContextProvider>
    </>
  );
}

export default App;
