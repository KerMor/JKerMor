/**
 * @defgroup jkermor JKerMor
 * @ingroup jarmosbase
 * @short Classes for simulation of KerMor reduced models
 * 
 * @section jkermor_dynsys JKerMor dynamical systems
 * This package allows to simulate certain reduced systems from the MatLab KerMor framework.
 * The basic system structure considered is
 * @f[
 *  \begin{align}
 * 	M(t,\mu)x'(t) &= A(t,\mu)x(t) + f(x(t),t,\mu) + B(t,\mu)u_i(t)\\
 * x(0) &= x_0(\mu)\\
 * y(t) &= C(t,\mu)x(t)
 *  \end{align} 
 * @f]
 * 
 * See the KerMor framework http://www.morepas.org/software/kermor for more information on reduced model generation and
 * export.
 * 
 * @{
 * @package kermor
 * @short @ref jkermor main package
 * 
 * @package kermor.dscomp
 * @short Contains classes for dynamical system components
 * 
 * @package kermor.kernel
 * @short Classes for kernels and expansions
 * 
 * @package kermor.test
 * @short Test classes
 * 
 * @package kermor.visual
 * @short Classes for visualization of simulation results
 * 
 * @package models.beam.dynlintimo
 * @short Timoshenko-Beam model related classes
 * 
 * This package contains classes involving the timoshenko-beam model from C. Strohmeyer (Erlangen).
 * Thus far this is work-in-progress.
 * 
 * @}
 * 
 * The JKerMor framework as a whole is published under the GNU GPL license stated below.
 * 
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with this program. If not, see
 * <http://www.gnu.org/licenses/>
 */
